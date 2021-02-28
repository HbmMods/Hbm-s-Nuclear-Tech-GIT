package com.hbm.tileentity.turret;

import java.util.List;

import com.hbm.handler.BulletConfiguration;

public class TileEntityTurretTauon extends TileEntityTurretBaseNT {

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
		return 7D;
	}

	@Override
	public double getTurretYawSpeed() {
		return 9D;
	}

	@Override
	public double getTurretPitchSpeed() {
		return 6D;
	}

	@Override
	public double getTurretElevation() {
		return 35D;
	}

	@Override
	public double getTurretDepression() {
		return 35D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}
}
