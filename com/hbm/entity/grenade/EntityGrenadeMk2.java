package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionLarge;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeMk2 extends EntityGrenadeBase {

	private static final String __OBFID = "CL_00001722";

	public EntityGrenadeMk2(World p_i1773_1_) {
		super(p_i1773_1_);
	}

    public EntityGrenadeMk2(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeMk2(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

	@Override
	public void explode() {

		if (!this.worldObj.isRemote) {
			this.setDead();
			ExplosionLarge.explode(worldObj, posX, posY, posZ, 7.5F, true, false, false);
		}
	}
}
