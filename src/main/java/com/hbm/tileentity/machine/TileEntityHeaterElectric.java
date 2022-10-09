package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.tile.IHeatSource;

public class TileEntityHeaterElectric extends TileEntityLoadedBase implements IHeatSource, IEnergyUser {

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

	@Override
	public int getHeatStored() {
		return 0;
	}

	@Override
	public void useUpHeat(int heat) {
		
	}
}
