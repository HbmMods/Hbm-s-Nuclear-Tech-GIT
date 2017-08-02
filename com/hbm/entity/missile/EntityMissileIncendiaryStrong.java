package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileIncendiaryStrong extends EntityMissileBaseAdvanced {

	public EntityMissileIncendiaryStrong(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileIncendiaryStrong(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		ExplosionLarge.explodeFire(worldObj, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 25.0F, true, true, true);
		ExplosionChaos.flameDeath(this.worldObj, (int)((float)this.posX + 0.5F), (int)((float)this.posY + 0.5F), (int)((float)this.posZ + 0.5F), 25);
	}
}
