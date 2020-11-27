package com.hbm.tileentity.machine;

import com.hbm.interfaces.IConsumer;

import net.minecraft.tileentity.TileEntity;

public class TileEntityHadronPower extends TileEntity implements IConsumer {
	
	public long power;
	public static final long maxPower = 1000000000;
	
    public boolean canUpdate() {
        return false;
    }

	@Override
	public void setPower(long i) {
		power = i;
		this.markDirty();
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
