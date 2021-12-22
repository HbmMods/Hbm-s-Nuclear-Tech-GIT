package com.hbm.tileentity.machine;

import com.hbm.inventory.recipes.CentrifugeRecipes;
import com.hbm.inventory.recipes.GasCentrifugeRecipes.PseudoFluidType;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

//TODO: move this trash to TileEntityMachineBase
//no seriously, this is dreadful
public class TileEntityMachineCentrifuge extends TileEntityMachineBase implements ISidedInventory, IEnergyUser {
	
	public int progress;
	public long power;
	public boolean isProgressing;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 200;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 2, 3, 4, 5 };
	private static final int[] slots_side = new int[] { 0, 1 };

	public TileEntityMachineCentrifuge() {
		super(6);
	}
	
	public String getName() {
		return "container.centrifuge";
	}
	

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 2 || i == 3 || i == 4 || i == 5) {
			return false;
		}

		if(i == 1) {
			return itemStack.getItem() instanceof IBatteryItem;
		}

		return !(itemStack.getItem() instanceof IBatteryItem);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		power = nbt.getLong("power");
		progress = nbt.getShort("progress");
		slots = new ItemStack[getSizeInventory()];

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setShort("progress", (short) progress);
		NBTTagList list = new NBTTagList();

		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return j != 0 || i != 1;
	}

	public int getCentrifugeProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}

	public long getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}

	public boolean canProcess() {

		if(slots[0] == null) {
			return false;
		}
		ItemStack[] out = CentrifugeRecipes.getOutput(slots[0]);
		
		if(out == null) {
			return false;
		}

		for(int i = 0; i < 4; i++) {

			//either the slot is null, the output is null or the output can be added to the existing slot
			if(slots[i + 2] == null)
				continue;

			if(out[i] == null)
				continue;

			if(slots[i + 2].isItemEqual(out[i]) && slots[i + 2].stackSize + out[i].stackSize <= out[i].getMaxStackSize())
				continue;

			return false;
		}

		return true;
	}

	private void processItem() {
		ItemStack[] out = CentrifugeRecipes.getOutput(slots[0]);

		for(int i = 0; i < 4; i++) {

			if(out[i] == null)
				continue;

			if(slots[i + 2] == null) {
				slots[i + 2] = out[i].copy();
			} else {
				slots[i + 2].stackSize += out[i].stackSize;
			}
		}

		this.decrStackSize(0, 1);
		this.markDirty();
	}

	public boolean hasPower() {
		return power > 0;
	}

	public boolean isProcessing() {
		return this.progress > 0;
	}
	
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.progress = data.getInteger("progress");
		this.isProgressing = data.getBoolean("isProgressing");
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			this.updateStandardConnections(worldObj, xCoord, yCoord, zCoord);

			power = Library.chargeTEFromItems(slots, 1, power, maxPower);

			if(hasPower() && isProcessing()) {
				this.power -= 200;

				if(this.power < 0) {
					this.power = 0;
				}
			}

			if(hasPower() && canProcess()) {
				isProgressing = true;
			} else {
				isProgressing = false;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("progress", progress);
			data.setBoolean("isProgressing", isProgressing);
			this.networkPack(data, 50);
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power),
					new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(xCoord, yCoord, zCoord),
					new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}

		if(hasPower() && canProcess()) {
			progress++;

			if(this.progress >= TileEntityMachineCentrifuge.processingSpeed) {
				this.progress = 0;
				this.processItem();
			}
		} else {
			progress = 0;
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;

	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
}
