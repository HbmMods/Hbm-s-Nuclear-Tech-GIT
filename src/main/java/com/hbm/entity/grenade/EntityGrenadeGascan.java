package com.hbm.entity.grenade;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeGascan extends EntityGrenadeBase {

	public EntityGrenadeGascan(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeGascan(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityGrenadeGascan(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if(!this.worldObj.isRemote) {
			this.setDead();
			this.worldObj.newExplosion((Entity) null, (float) this.posX, (float) this.posY, (float) this.posZ, 5.0F, true, false);
		}
	}
}
