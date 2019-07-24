package com.hbm.interfaces;

import com.hbm.entity.projectile.EntityBulletBase;

import net.minecraft.entity.Entity;

public interface IBulletRicochetBehavior {
	
	//block is hit, bullet ricochets
	public void behaveBlockRicochet(EntityBulletBase bullet, int x, int y, int z);

}
