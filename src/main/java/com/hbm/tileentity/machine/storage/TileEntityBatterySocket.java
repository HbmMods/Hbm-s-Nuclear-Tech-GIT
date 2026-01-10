package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.container.ContainerBatterySocket;
import com.hbm.inventory.gui.GUIBatterySocket;
import com.hbm.util.BufferUtil;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import api.hbm.tile.IInfoProviderEC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBatterySocket extends TileEntityBatteryBase implements IRORValueProvider, IRORInteractive, IInfoProviderEC {

	public long[] log = new long[20];
	public long delta = 0;
	
	public ItemStack syncStack;
	
	public TileEntityBatterySocket() {
		super(1);
	}
	
	@Override public String getName() { return "container.batterySocket"; }

	@Override
	public void updateEntity() {
		long prevPower = this.getPower();
		
		super.updateEntity();
		
		if(!worldObj.isRemote) {

			long avg = (this.getPower() + prevPower) / 2;
			this.delta = avg - this.log[0];

			for(int i = 1; i < this.log.length; i++) {
				this.log[i - 1] = this.log[i];
			}

			this.log[19] = avg;
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(delta);
		BufferUtil.writeItemStack(buf, this.slots[0]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		delta = buf.readLong();
		this.syncStack = BufferUtil.readItemStack(buf);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(stack.getItem() instanceof IBatteryItem) {
			if(i == mode_input && ((IBatteryItem)stack.getItem()).getCharge(stack) == 0) return true;
			if(i == mode_output && ((IBatteryItem)stack.getItem()).getCharge(stack) == ((IBatteryItem)stack.getItem()).getMaxCharge(stack)) return true;
		}
		return false;
	}

	@Override public int[] getAccessibleSlotsFromSide(int side) { return new int[] {0}; }

	@Override public long getPower() { return powerFromStack(slots[0]); }
	@Override public long getMaxPower() { return maxPowerFromStack(slots[0]); }
	
	@Override
	public void setPower(long power) {
		if(slots[0] == null || !(slots[0].getItem() instanceof IBatteryItem)) return;
		((IBatteryItem) slots[0].getItem()).setCharge(slots[0], power);
	}
	
	public static long powerFromStack(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof IBatteryItem)) return 0;
		return ((IBatteryItem) stack.getItem()).getCharge(stack);
	}
	
	public static long maxPowerFromStack(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof IBatteryItem)) return 0;
		return ((IBatteryItem) stack.getItem()).getMaxCharge(stack);
	}

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

	@Override
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
	
	@Override
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

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerBatterySocket(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIBatterySocket(player.inventory, this); }
	
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
	public String[] getFunctionInfo() {
		return new String[] {
				PREFIX_VALUE + "fill",
				PREFIX_VALUE + "fillpercent",
				PREFIX_VALUE + "delta",
				PREFIX_FUNCTION + "setmode" + NAME_SEPARATOR + "mode",
				PREFIX_FUNCTION + "setmode" + NAME_SEPARATOR + "mode" + PARAM_SEPARATOR + "fallback",
				PREFIX_FUNCTION + "setredmode" + NAME_SEPARATOR + "mode",
				PREFIX_FUNCTION + "setredmode" + NAME_SEPARATOR + "mode" + PARAM_SEPARATOR + "fallback",
				PREFIX_FUNCTION + "setpriority" + NAME_SEPARATOR + "priority",
		};
	}

	@Override
	public String provideRORValue(String name) {
		if((PREFIX_VALUE + "fill").equals(name))		return "" + this.getPower();
		if((PREFIX_VALUE + "fillpercent").equals(name))	return "" + this.getPower() * 100 / (Math.min(this.getMaxPower(), 1));
		if((PREFIX_VALUE + "delta").equals(name))		return "" + delta;
		return null;
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		
		if((PREFIX_FUNCTION + "setmode").equals(name) && params.length > 0) {
			int mode = IRORInteractive.parseInt(params[0], 0, 3);
			
			if(mode != this.redLow) {
				this.redLow = (short) mode;
				this.markChanged();
				return null;
			} else if(params.length > 1) {
				int altmode = IRORInteractive.parseInt(params[1], 0, 3);
				this.redLow = (short) altmode;
				this.markChanged();
				return null;
			}
			return null;
		}
		
		if((PREFIX_FUNCTION + "setredmode").equals(name) && params.length > 0) {
			int mode = IRORInteractive.parseInt(params[0], 0, 3);
			
			if(mode != this.redHigh) {
				this.redHigh = (short) mode;
				this.markChanged();
				return null;
			} else if(params.length > 1) {
				int altmode = IRORInteractive.parseInt(params[1], 0, 3);
				this.redHigh = (short) altmode;
				this.markChanged();
				return null;
			}
			return null;
		}
		
		if((PREFIX_FUNCTION + "setpriority").equals(name) && params.length > 0) {
			int priority = IRORInteractive.parseInt(params[0], 0, 2) + 1;
			ConnectionPriority p = EnumUtil.grabEnumSafely(ConnectionPriority.class, priority);
			this.priority = p;
			this.markChanged();
			return null;
		}
		return null;
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setLong(CompatEnergyControl.L_DIFF_HE, (log[0] - log[19]) / 20L);
	}
}
