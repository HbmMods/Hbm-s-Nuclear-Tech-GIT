package com.hbm.interfaces;

import com.hbm.entity.projectile.EntityBulletBase;

public interface IBulletRicochetBehavior {
	
	//block is hit, bullet ricochets
	public void behaveBlockRicochet(EntityBulletBase bullet, int x, int y, int z);

}
