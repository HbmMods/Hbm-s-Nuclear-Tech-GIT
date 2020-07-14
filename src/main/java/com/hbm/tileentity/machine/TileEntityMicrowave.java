package com.hbm.tileentity.machine;

import com.hbm.interfaces.IConsumer;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMicrowave extends TileEntityMachineBase implements IConsumer {
	
	public long power;
	public static final long maxPower = 50000;
	public static final int consumption = 50;
	public static final int maxTime = 300;
	public int time;
	public int speed;
	public static final int maxSpeed = 5;

	public TileEntityMicrowave() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.microwave";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 2, power, maxPower);
			
			if(canProcess()) {
				
				if(speed == maxSpeed) {
					worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
					worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 7.5F, true, true);
					return;
				}
				
				power -= consumption;
				time += speed;
				
				if(time >= maxTime) {
					process();
					time = 0;
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("time", time);
			data.setInteger("speed", speed);
			networkPack(data, 50);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		power = data.getLong("power");
		time = data.getInteger("time");
		speed = data.getInteger("speed");
	}
	
	public void handleButtonPacket(int value, int meta) {
		
		if(value == 0)
			speed++;
		
		if(value == 1)
			speed--;
		
		if(speed < 0)
			speed = 0;
		
		if(speed > maxSpeed)
			speed = maxSpeed;
	}
	
	private void process() {
		
		ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(slots[0]).copy();
		
		if(slots[1] == null) {
			slots[1] = stack;
		} else {
			slots[1].stackSize += stack.stackSize;
		}
		
		this.decrStackSize(0, 1);
		
		this.markDirty();
	}
	
	private boolean canProcess() {
		
		if(speed  == 0)
			return false;
		
		if(power < consumption)
			return false;
		
		if(slots[0] != null && FurnaceRecipes.smelting().getSmeltingResult(slots[0]) != null) {
			
			ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(slots[0]);
			
			if(slots[1] == null)
				return true;
			
			if(!stack.isItemEqual(slots[1]))
				return false;
			
			return stack.stackSize + slots[1].stackSize <= stack.getMaxStackSize();
		}
		
		return false;
	}
	
	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (time * i) / maxTime;
	}
	
	public int getSpeedScaled(int i) {
		return (speed * i) / maxSpeed;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
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
