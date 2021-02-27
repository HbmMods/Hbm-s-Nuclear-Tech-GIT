package com.hbm.tileentity.turret;

import java.util.List;

import com.hbm.handler.BulletConfiguration;

public class TileEntityTurretJeremy extends TileEntityTurretBaseNT {

	@Override
	protected List<BulletConfiguration> getAmmoList() {
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
}
