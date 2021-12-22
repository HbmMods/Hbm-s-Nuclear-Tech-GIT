package com.hbm.entity.grenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeDynamite extends EntityGrenadeBouncyBase {

	public EntityGrenadeDynamite(World world) {
		super(world);
	}

	public EntityGrenadeDynamite(World world, EntityLivingBase living) {
		super(world, living);
	}

	public EntityGrenadeDynamite(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public void explode() {
		worldObj.newExplosion(this, posX, posY + 0.25D, posZ, 3F, false, false);
		this.setDead();
	}

	@Override
	protected int getMaxTimer() {
		return 60;
	}

	@Override
	protected double getBounceMod() {
		return 0.5D;
	}
}
