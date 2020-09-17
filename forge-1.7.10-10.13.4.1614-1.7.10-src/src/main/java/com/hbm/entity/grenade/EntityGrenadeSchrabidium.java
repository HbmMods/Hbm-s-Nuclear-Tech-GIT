package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeSchrabidium extends EntityGrenadeBouncyBase {

	public EntityGrenadeSchrabidium(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeSchrabidium(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityGrenadeSchrabidium(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if(!this.worldObj.isRemote) {
			this.setDead();
			ExplosionChaos.schrab(this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ, 50, 50);
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1.5F, true);
		}
	}

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_schrabidium);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}

}
