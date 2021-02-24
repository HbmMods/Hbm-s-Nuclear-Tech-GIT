package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.guncfg.Gun75BoltFactory;

public class TileEntityTurretChekhov extends TileEntityTurretBaseNT {

	static List<BulletConfiguration> configs = new ArrayList();
	
	//because cramming it into the ArrayList's constructor with nested curly brackets and all that turned out to be not as pretty
	//also having a floaty `static` like this looks fun
	//idk if it's just me though
	static {
		configs.add(Gun75BoltFactory.get75BoltConfig());
	}
	
	@Override
	protected List<BulletConfiguration> getAmmoList() {
		return null;
	}

	@Override
	public String getName() {
		return "container.turretChekhov";
	}

}
