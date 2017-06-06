package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionChaos;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGrenadeZOMG extends EntityGrenadeBase
{
    private static final String __OBFID = "CL_00001722";

    public EntityGrenadeZOMG(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeZOMG(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            this.setDead();
            ExplosionChaos.zomgMeSinPi(this.worldObj, this.posX, this.posY, this.posZ, 100, this.getThrower(), this);
        }
    }

}
