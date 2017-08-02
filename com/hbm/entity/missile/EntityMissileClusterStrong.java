package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionChaos;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileClusterStrong extends EntityMissileBaseAdvanced {

	public EntityMissileClusterStrong(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileClusterStrong(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
		this.isCluster = true;
	}

	@Override
	public void onImpact() {
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 15F, true);
        ExplosionChaos.cluster(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 50, 100);
	}
	
	@Override
	public void cluster() {
		this.onImpact();
	}
}
