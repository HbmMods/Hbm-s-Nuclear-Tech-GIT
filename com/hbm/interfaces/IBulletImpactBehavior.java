package com.hbm.interfaces;

import com.hbm.entity.projectile.EntityBulletBase;

public interface IBulletImpactBehavior {
	
	//block is hit, bullet dies
	public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z);

}
