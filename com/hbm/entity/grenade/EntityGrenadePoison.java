package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionNukeGeneric;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadePoison extends EntityGrenadeBase
{
    private static final String __OBFID = "CL_00001722";

    public EntityGrenadePoison(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadePoison(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    @Override
    public void explode() {
    	

        if (!this.worldObj.isRemote)
        {
            this.setDead();
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.0F, true);
            ExplosionNukeGeneric.wasteNoSchrab(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 10);
        }
    }

}
