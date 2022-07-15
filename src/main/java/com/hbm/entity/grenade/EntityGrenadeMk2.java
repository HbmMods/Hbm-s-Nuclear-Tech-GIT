package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.AmmoHandGrenade;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeMk2 extends EntityGrenadeBouncyBase {


	public EntityGrenadeMk2(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeMk2(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityGrenadeMk2(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if(!this.worldObj.isRemote) {
			this.setDead();
			ExplosionLarge.explode(worldObj, posX, posY, posZ, 7.5F, true, false, false);
		}
	}

	@Override
	protected int getMaxTimer() {
		return AmmoHandGrenade.GENERIC.fuse * 20;
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
