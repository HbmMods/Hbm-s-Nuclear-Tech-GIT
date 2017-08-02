package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionThermo;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileEndo extends EntityMissileBaseAdvanced {

	public EntityMissileEndo(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileEndo(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10.0F, true);
		ExplosionThermo.freeze(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 30);
		ExplosionThermo.freezer(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 40);
	}
}
