package com.hbm.entity.grenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeNuke extends EntityGrenadeBase {
	public int count = 2;

	public EntityGrenadeNuke(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeNuke(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityGrenadeNuke(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if(!this.worldObj.isRemote) {
			// this.setDead();
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 30F, true);
		}
	}

}
