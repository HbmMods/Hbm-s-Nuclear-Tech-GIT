package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import io.netty.buffer.ByteBuf;

public class TileEntityMachinePumpElectric extends TileEntityMachinePumpBase implements IEnergyReceiverMK2 {
	
	public long power;
	public static final long maxPower = 10_000;
	
	public TileEntityMachinePumpElectric() {
		super();
		water = new FluidTank(Fluids.WATER, electricSpeed * 100);
	}
	
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 20 == 0) for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
		}
		
		super.updateEntity();
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(this.power);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
	}

	@Override
	protected boolean canOperate() {
		return power >= 1_000 && water.getFill() < water.getMaxFill();
	}

	@Override
	protected void operate() {
		this.power -= 1_000;
		water.setFill(Math.min(water.getFill() + electricSpeed, water.getMaxFill()));
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
