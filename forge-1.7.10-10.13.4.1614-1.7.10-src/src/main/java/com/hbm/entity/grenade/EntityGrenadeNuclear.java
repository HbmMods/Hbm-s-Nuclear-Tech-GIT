package com.hbm.entity.grenade;

import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeNuclear extends EntityGrenadeBouncyBase {

	public EntityGrenadeNuclear(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeNuclear(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityGrenadeNuclear(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if(!this.worldObj.isRemote) {
			this.setDead();

			this.worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(worldObj, BombConfig.nukaRadius, posX, posY, posZ));
			if(rand.nextInt(100) == 0) {
				ExplosionParticleB.spawnMush(this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ);
			} else {
				ExplosionParticle.spawnMush(this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ);
			}
		}
	}

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_nuclear);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
