package com.hbm.entity.missile;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.main.MainRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityMissileMirv extends EntityMissileBaseAdvanced {

	public EntityMissileMirv(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileMirv(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
		this.isCluster = true;
	}

	@Override
	public void onImpact() {
		EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(this.worldObj);
    	entity.posX = this.posX;
    	entity.posY = this.posY;
    	entity.posZ = this.posZ;
    	entity.destructionRange = MainRegistry.missileRadius;
    	entity.speed = 25;
    	entity.coefficient = 10.0F;
    	
    	this.worldObj.spawnEntityInWorld(entity);

		EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(this.worldObj, 1000);
    	entity2.posX = this.posX;
    	entity2.posY = this.posY - 9;
    	entity2.posZ = this.posZ;
    	this.worldObj.spawnEntityInWorld(entity2);
	}
	
	@Override
	public void cluster() {
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 15.0F, true);
		ExplosionChaos.mirv(this.worldObj,this.posX, this.posY, this.posZ);
	}
}
