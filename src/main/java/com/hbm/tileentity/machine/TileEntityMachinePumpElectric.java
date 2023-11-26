package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMachinePumpElectric extends TileEntityMachinePumpBase implements IEnergyUser {
	
	public long power;
	public static final long maxPower = 10_000;
	
	public TileEntityMachinePumpElectric() {
		super();
		water = new FluidTank(Fluids.WATER, 1_000_000);
	}
	
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 20 == 0) for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
		}
		
		super.updateEntity();
	}
	
	protected NBTTagCompound getSync() {
		NBTTagCompound data = super.getSync();
		data.setLong("power", power);
		return data;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		this.power = nbt.getLong("power");
	}

	@Override
	protected boolean canOperate() {
		return power >= 1_000 && water.getFill() < water.getMaxFill();
	}

	@Override
	protected void operate() {
		this.power -= 1_000;
		water.setFill(Math.min(water.getFill() + 10_000, water.getMaxFill()));
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}
}
