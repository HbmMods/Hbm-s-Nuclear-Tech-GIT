package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;

public class TileEntityTurretFriendly extends TileEntityTurretChekhov {

	static List<Integer> configs = new ArrayList();
	
	static {
		configs.add(BulletConfigSyncingUtil.R5_NORMAL);
		configs.add(BulletConfigSyncingUtil.R5_EXPLOSIVE);
		configs.add(BulletConfigSyncingUtil.R5_DU);
		configs.add(BulletConfigSyncingUtil.R5_STAR);
		configs.add(BulletConfigSyncingUtil.CHL_R5);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return configs;
	}

	@Override
	public String getName() {
		return "container.turretFriendly";
	}

	@Override
	public int getDelay() {
		return 5;
	}
}
