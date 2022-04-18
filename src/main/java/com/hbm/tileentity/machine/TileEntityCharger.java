package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;

public class TileEntityCharger extends TileEntityLoadedBase implements IEnergyUser {

	@Override
	public long getPower() {
		return 0;
	}

	@Override
	public long getMaxPower() {
		return 0;
	}

	@Override
	public void setPower(long power) {
		
	}
}
