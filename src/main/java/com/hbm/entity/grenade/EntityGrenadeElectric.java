package com.hbm.entity.grenade;

import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.AmmoHandGrenade;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;

public class EntityGrenadeElectric extends EntityGrenadeBouncyBase
{
    private static final String __OBFID = "CL_00001722";

    public EntityGrenadeElectric(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeElectric(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeElectric(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            this.setDead();
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.0F, true);
        }
            this.worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, this.posX, this.posY, this.posZ));
    }

	@Override
	protected int getMaxTimer() {
		return AmmoHandGrenade.ELECTRIC.fuse * 20;
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}

}
