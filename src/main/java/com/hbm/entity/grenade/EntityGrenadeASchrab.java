package com.hbm.entity.grenade;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeASchrab extends EntityGrenadeBase {

	public EntityGrenadeASchrab(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeASchrab(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityGrenadeASchrab(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if (!this.worldObj.isRemote) {
			EntityNukeExplosionMK3 ex = EntityNukeExplosionMK3.statFacFleija(worldObj, posX, posY, posZ, BombConfig.aSchrabRadius);
			if(!ex.isDead) {
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 100.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
				worldObj.spawnEntityInWorld(ex);
	
				EntityCloudFleija cloud = new EntityCloudFleija(this.worldObj, BombConfig.aSchrabRadius);
				cloud.posX = this.posX;
				cloud.posY = this.posY;
				cloud.posZ = this.posZ;
				this.worldObj.spawnEntityInWorld(cloud);
			}
			
			this.setDead();
		}
	}
}
