package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileStrong extends EntityMissileBaseAdvanced {

	public EntityMissileStrong(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileStrong(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		ExplosionLarge.explode(worldObj, posX, posY, posZ, 25.0F, true, true, true);
	}

}
