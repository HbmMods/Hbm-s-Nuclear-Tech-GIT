package com.hbm.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityCombineBallNT extends EntityBulletBaseNT {

	public EntityCombineBallNT(World world, int config, EntityLivingBase shooter) {
		super(world, config, shooter);
		overrideDamage = 1000;
	}

	@Override
	public void setDead() {
		super.setDead();
		worldObj.createExplosion(this.getThrower(), posX, posY, posZ, 2, false);
	}
}
