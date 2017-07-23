package com.hbm.entity.grenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import com.hbm.explosion.ExplosionChaos;

public class EntityGrenadeCluster extends EntityGrenadeBase
{
    private static final String __OBFID = "CL_00001722";

    public EntityGrenadeCluster(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeCluster(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    @Override
    public void explode() {

        if (!this.worldObj.isRemote)
        {
            this.setDead();
            ExplosionChaos.cluster(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 10, 50);
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1.5F, true);
        }
    }
}
