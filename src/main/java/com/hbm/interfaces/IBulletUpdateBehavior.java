package com.hbm.interfaces;

import com.hbm.entity.projectile.EntityBulletBase;

public interface IBulletUpdateBehavior {
	
	//once every update, for lcokon, steering and other memes
	public void behaveUpdate(EntityBulletBase bullet);

}
