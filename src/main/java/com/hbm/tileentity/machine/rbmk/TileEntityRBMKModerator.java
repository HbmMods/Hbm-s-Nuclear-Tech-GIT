package com.hbm.tileentity.machine.rbmk;

import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.handler.neutron.RBMKNeutronHandler.RBMKType;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

public class TileEntityRBMKModerator extends TileEntityRBMKBase {

	@Override
	public void onMelt(int reduce) {
		
		int count = 2 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.GRAPHITE);
		}
		
		super.onMelt(reduce);
	}

	@Override
	public RBMKType getRBMKType() {
		return RBMKType.MODERATOR;
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.MODERATOR;
	}
}
