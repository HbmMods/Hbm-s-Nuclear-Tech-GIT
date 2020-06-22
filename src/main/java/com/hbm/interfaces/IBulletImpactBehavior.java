package com.hbm.interfaces;

import com.hbm.entity.projectile.EntityBulletBase;

public interface IBulletImpactBehavior {
	
	//block is hit, bullet dies
	//also called when an entity is hit but with -1 coords, so beware
	public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z);

}
