package com.hbm.tileentity.machine.storage;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerBatterySocket;
import com.hbm.inventory.gui.GUIBatterySocket;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.energymk2.IEnergyConductorMK2;
import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.energymk2.Nodespace;
import api.hbm.energymk2.Nodespace.PowerNode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBatterySocket extends TileEntityMachineBase implements IEnergyConductorMK2, IEnergyProviderMK2, IEnergyReceiverMK2, IControlReceiver, IGUIProvider {

	public long[] log = new long[20];
	public long delta = 0;
	public byte lastRedstone = 0;
	public long prevPowerState = 0;
	
	public static final int mode_input = 0;
	public static final int mode_buffer = 1;
	public static final int mode_output = 2;
	public static final int mode_none = 3;
	public short redLow = 0;
	public short redHigh = 2;
	public ConnectionPriority priority = ConnectionPriority.LOW;
	
	public int renderPack = -1;

	protected PowerNode node;
	
	public TileEntityBatterySocket() {
		super(1);
	}
	
	@Override public String getName() { return "container.batterySocket"; }

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			if(priority == null || priority.ordinal() == 0 || priority.ordinal() == 4) {
				priority = ConnectionPriority.LOW;
			}

			long prevPower = this.getPower();
			
			if(this.node == null || this.node.expired) {

				this.node = (PowerNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, Nodespace.THE_POWER_PROVIDER);

				if(this.node == null || this.node.expired) {
					this.node = this.createNode();
					UniNodespace.createNode(worldObj, this.node);
				}
			}
			
			if(this.node != null && this.node.hasValidNet()) switch(this.getRelevantMode(false)) {
			case mode_input: this.node.net.removeProvider(this); this.node.net.addReceiver(this); break;
			case mode_output: this.node.net.addProvider(this); this.node.net.removeReceiver(this); break;
			case mode_buffer: this.node.net.addProvider(this); this.node.net.addReceiver(this); break;
			case mode_none: this.node.net.removeProvider(this); this.node.net.removeReceiver(this); break;
			}
			
			byte comp = this.getComparatorPower();
			if(comp != this.lastRedstone) this.markDirty();
			this.lastRedstone = comp;

			long avg = (this.getPower() + prevPower) / 2;
			this.delta = avg - this.log[0];

			for(int i = 1; i < this.log.length; i++) {
				this.log[i - 1] = this.log[i];
			}

			this.log[19] = avg;

			prevPowerState = this.getPower();
			
			this.networkPackNT(100);
		}
	}
	
	@Override
	public PowerNode createNode() {
		return new PowerNode(this.getPortPos()).setConnections(this.getConPos());
	}

	public byte getComparatorPower() {
		double frac = (double) this.getPower() / (double) this.getMaxPower() * 15D;
		return (byte) (MathHelper.clamp_int((int) frac + 1, 0, 15)); //to combat eventual rounding errors with the FEnSU's stupid maxPower
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(this.node != null) {
				UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, Nodespace.THE_POWER_PROVIDER);
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		
		int renderPack = -1;
		if(slots[0] != null && slots[0].getItem() == ModItems.battery_pack) {
			renderPack = slots[0].getItemDamage();
		}

		buf.writeInt(renderPack);
		buf.writeLong(delta);
		buf.writeShort(redLow);
		buf.writeShort(redHigh);
		buf.writeByte(priority.ordinal());
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		renderPack = buf.readInt();
		delta = buf.readLong();
		redLow = buf.readShort();
		redHigh = buf.readShort();
		priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, buf.readByte());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
		this.lastRedstone = nbt.getByte("lastRedstone");
		this.priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, nbt.getByte("priority"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setShort("redLow", redLow);
		nbt.setShort("redHigh", redHigh);
		nbt.setByte("lastRedstone", lastRedstone);
		nbt.setByte("priority", (byte) this.priority.ordinal());
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(stack.getItem() instanceof IBatteryItem) {
			if(i == mode_input && ((IBatteryItem)stack.getItem()).getCharge(stack) == 0) return true;
			if(i == mode_output && ((IBatteryItem)stack.getItem()).getCharge(stack) == ((IBatteryItem)stack.getItem()).getMaxCharge(stack)) return true;
		}
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return stack.getItem() instanceof IBatteryItem;
	}

	@Override public int[] getAccessibleSlotsFromSide(int side) { return new int[] {0}; }

	@Override
	public long getPower() {
		if(slots[0] == null || !(slots[0].getItem() instanceof IBatteryItem)) return 0;
		return ((IBatteryItem) slots[0].getItem()).getCharge(slots[0]);
	}
	
	@Override
	public void setPower(long power) {
		if(slots[0] == null || !(slots[0].getItem() instanceof IBatteryItem)) return;
		((IBatteryItem) slots[0].getItem()).setCharge(slots[0], power);
	}

	@Override
	public long getMaxPower() {
		if(slots[0] == null || !(slots[0].getItem() instanceof IBatteryItem)) return 0;
		return ((IBatteryItem) slots[0].getItem()).getMaxCharge(slots[0]);
	}

	@Override public boolean allowDirectProvision() { return false; }
	@Override public ConnectionPriority getPriority() { return this.priority; }

	@Override public long getProviderSpeed() {
		if(slots[0] == null || !(slots[0].getItem() instanceof IBatteryItem)) return 0;
		int mode = this.getRelevantMode(true);
		return mode == mode_output || mode == mode_buffer ? ((IBatteryItem) slots[0].getItem()).getDischargeRate(slots[0]) : 0;
	}

	@Override public long getReceiverSpeed() {
		if(slots[0] == null || !(slots[0].getItem() instanceof IBatteryItem)) return 0;
		int mode = this.getRelevantMode(true);
		return mode == mode_input || mode == mode_buffer ? ((IBatteryItem) slots[0].getItem()).getChargeRate(slots[0]) : 0;
	}
	
	public BlockPos[] getPortPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new BlockPos[] {
				new BlockPos(xCoord, yCoord, zCoord),
				new BlockPos(xCoord - dir.offsetX, yCoord, zCoord - dir.offsetZ),
				new BlockPos(xCoord + rot.offsetX, yCoord, zCoord + rot.offsetZ),
				new BlockPos(xCoord - dir.offsetX + rot.offsetX, yCoord, zCoord - dir.offsetZ + rot.offsetZ)
		};
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX + rot.offsetX, yCoord, zCoord + dir.offsetZ + rot.offsetZ, dir),
				
				new DirPos(xCoord - dir.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),
				
				new DirPos(xCoord + rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2, rot),
				new DirPos(xCoord + rot.offsetX * 2 - dir.offsetX, yCoord, zCoord + rot.offsetZ * 2 - dir.offsetZ, rot),
				
				new DirPos(xCoord - rot.offsetX, yCoord, zCoord - rot.offsetZ, rot.getOpposite()),
				new DirPos(xCoord - rot.offsetX - dir.offsetX, yCoord, zCoord - rot.offsetZ - dir.offsetZ, rot.getOpposite())
		};
	}

	private short modeCache = 0;
	
	public short getRelevantMode(boolean useCache) {
		if(useCache) return this.modeCache;
		boolean powered = false;
		for(BlockPos pos : getPortPos()) if(worldObj.isBlockIndirectlyGettingPowered(pos.getX(), pos.getY(), pos.getZ())) { powered = true; break; }
		this.modeCache = powered ? this.redHigh : this.redLow;
		return this.modeCache;
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerBatterySocket(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIBatterySocket(player.inventory, this); }

	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("low")) {
			this.redLow++;
			if(this.redLow > 3) this.redLow = 0;
		}
		if(data.hasKey("high")) {
			this.redHigh++;
			if(this.redHigh > 3) this.redHigh = 0;
		}
		if(data.hasKey("priority")) {
			int ordinal = this.priority.ordinal();
			ordinal++;
			if(ordinal > ConnectionPriority.HIGH.ordinal()) ordinal = ConnectionPriority.LOW.ordinal();
			this.priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, ordinal);
		}
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
					yCoord + 2,
					zCoord + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
