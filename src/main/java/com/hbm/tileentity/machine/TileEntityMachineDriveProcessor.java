package com.hbm.tileentity.machine;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerDriveProcessor;
import com.hbm.inventory.gui.GUIMachineDriveProcessor;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.EnumUtil;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineDriveProcessor extends TileEntityMachineBase implements IGUIProvider, IControlReceiver, IEnergyReceiverMK2 {

	public long power;
	public long maxPower = 2_000;

	public boolean isProcessing;
	public int progress;
	public int maxProgress = 100; // 5 seconds

	public String status = "";
	public boolean hasDrive = false;

	private int lastTier;

	public TileEntityMachineDriveProcessor() {
		super(4);
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {

			power = Library.chargeTEFromItems(slots, 3, power, maxPower);
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);

			if(power < maxPower * 0.75) {
				isProcessing = false;
				status = EnumChatFormatting.RED + "No power ";
			} else if(slots[0] == null || slots[0].getItem() != ModItems.full_drive) {
				isProcessing = false;
				status = "";
			} else if(getProcessingTier() < ItemVOTVdrive.getProcessingTier(slots[0])) {
				isProcessing = false;
				status = EnumChatFormatting.RED + "Low tier ";
			}

			if(lastTier != getProcessingTier()) {
				status = "";
			}

			if(isProcessing) {
				power -= 200;

				status = EnumChatFormatting.GREEN + "" + EnumChatFormatting.ITALIC + "Processing  ";
				progress++;

				if(progress >= maxProgress) {
					progress = 0;
					isProcessing = false;
					ItemVOTVdrive.setProcessed(slots[0], true);
					status = EnumChatFormatting.GREEN + "Done! ";
				}
			} else {
				progress = 0;
			}

			lastTier = getProcessingTier();
			hasDrive = slots[0] != null && slots[0].getItem() == ModItems.full_drive;

			networkPackNT(15);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeLong(power);
		buf.writeBoolean(isProcessing);
		buf.writeInt(progress);
		buf.writeBoolean(hasDrive);

		BufferUtil.writeString(buf, status);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		power = buf.readLong();
		isProcessing = buf.readBoolean();
		progress = buf.readInt();
		hasDrive = buf.readBoolean();

		status = BufferUtil.readString(buf);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		nbt.setBoolean("isProcessing", isProcessing);
		nbt.setInteger("progress", progress);
		nbt.setString("status", status);
		nbt.setInteger("lastTier", lastTier);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		isProcessing = nbt.getBoolean("isProcessing");
		progress = nbt.getInteger("progress");
		status = nbt.getString("status");
		lastTier = nbt.getInteger("lastTier");
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return isUseableByPlayer(player);
	}

	private int getProcessingTier() {
		if(slots[2] == null || slots[2].getItem() != ModItems.circuit) return 0;
		
		EnumCircuitType num = EnumUtil.grabEnumSafely(EnumCircuitType.class, slots[2].getItemDamage());

		switch(num) {
		case PROCESST1: return 1;
		case PROCESST2: return 2;
		case PROCESST3: return 3;
		default: return 0;
		}
	}

	private void processDrive(boolean process) {
		if(!process) {
			isProcessing = false;
			return;
		}

		if(power < maxPower * 0.75) return;
		if(slots[0] == null || slots[0].getItem() != ModItems.full_drive) return;
		if(ItemVOTVdrive.getProcessed(slots[0])) return;

		// Check that our installed upgrade is a high enough tier
		if(getProcessingTier() >= ItemVOTVdrive.getProcessingTier(slots[0])) {
			isProcessing = true;
		}
	}

	private void cloneDrive() {
		if(power < maxPower * 0.75) return;
		if(slots[0] == null || slots[0].getItem() != ModItems.full_drive) return;
		if(slots[1] == null || slots[1].getItem() != ModItems.hard_drive) {
			status = EnumChatFormatting.RED + "No target ";
			return;
		}

		ItemVOTVdrive.markCopied(slots[0]);
		slots[1] = slots[0].copy();

		status = EnumChatFormatting.GREEN + "Drive cloned ";
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("process")) {
			processDrive(data.getBoolean("process"));
		}

		if(data.hasKey("clone")) {
			cloneDrive();
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerDriveProcessor(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineDriveProcessor(player.inventory, this);
	}

	@Override
	public String getName() {
		return "container.machineDriveProcessor";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
				xCoord - 1,
				yCoord,
				zCoord - 1,
				xCoord + 2,
				yCoord + 1,
				zCoord + 2
			);
		}
		
		return bb;
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }
	
}
