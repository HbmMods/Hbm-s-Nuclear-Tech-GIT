package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionChaos;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileRain extends EntityMissileBaseAdvanced {

	public EntityMissileRain(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileRain(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
		this.isCluster = true;
	}

	@Override
	public void onImpact() {
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 25F, true);
        ExplosionChaos.cluster(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 100, 100);
	}
	
	@Override
	public void cluster() {
		this.onImpact();
	}
}
