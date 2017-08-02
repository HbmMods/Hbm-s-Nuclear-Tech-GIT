package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileBurst extends EntityMissileBaseAdvanced {

	public EntityMissileBurst(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileBurst(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		for(int i = 0; i < 4; i++)
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 50.0F, true);
		ExplosionLarge.explode(worldObj, posX, posY, posZ, 50.0F, true, true, true);
	}
}
