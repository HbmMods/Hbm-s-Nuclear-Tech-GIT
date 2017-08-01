package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionChaos;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileCluster extends EntityMissileBaseAdvanced {

	public EntityMissileCluster(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileCluster(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
		this.isCluster = true;
	}

	@Override
	public void onImpact() {
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 5F, true);
        ExplosionChaos.cluster(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 25, 100);
	}
	
	@Override
	public void cluster() {
		this.onImpact();
	}
}
