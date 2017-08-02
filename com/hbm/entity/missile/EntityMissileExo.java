package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionThermo;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileExo extends EntityMissileBaseAdvanced {

	public EntityMissileExo(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileExo(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10.0F, true);
		ExplosionThermo.scorch(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 30);
		ExplosionThermo.setEntitiesOnFire(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 40);
	}
}
