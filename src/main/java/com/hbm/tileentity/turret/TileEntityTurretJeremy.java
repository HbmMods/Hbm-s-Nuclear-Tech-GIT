package com.hbm.tileentity.turret;

import java.util.List;

public class TileEntityTurretJeremy extends TileEntityTurretBaseNT {

	@Override
	protected List<Integer> getAmmoList() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}
	
	@Override
	public double getDecetorGrace() {
		return 10D;
	}

	@Override
	public double getTurretDepression() {
		return 35D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}

	@Override
	public void updateFiringTick() {
		
	}
}
