package com.hbm.entity.grenade;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeZOMG extends EntityGrenadeBouncyBase {

	public EntityGrenadeZOMG(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeZOMG(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityGrenadeZOMG(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if(!this.worldObj.isRemote) {
			this.setDead();
			
			EntityNukeExplosionMK3 ex = new EntityNukeExplosionMK3(worldObj);
			ex.posX = posX;
			ex.posY = posY;
			ex.posZ = posZ;
			ex.destructionRange = 50;
			ex.speed = BombConfig.blastSpeed;
			ex.coefficient = 1.0F;
			ex.waste = false;
			worldObj.spawnEntityInWorld(ex);
			
			worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 100000.0F, 1.0F);
			
			EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(worldObj, 50);
			cloud.posX = posX;
			cloud.posY = posY;
			cloud.posZ = posZ;
			worldObj.spawnEntityInWorld(cloud);
		}
	}

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_tau);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}

}
