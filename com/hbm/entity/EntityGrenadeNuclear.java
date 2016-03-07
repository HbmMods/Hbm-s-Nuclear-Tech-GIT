package com.hbm.entity;

import com.hbm.explosion.ExplosionParticle;
import com.hbm.explosion.ExplosionParticleB;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGrenadeNuclear extends EntityThrowable
{
    private static final String __OBFID = "CL_00001722";

    public EntityGrenadeNuclear(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeNuclear(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeNuclear(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
	protected void onImpact(MovingObjectPosition p_70184_1_)
    {
        if (p_70184_1_.entityHit != null)
        {
            int b0 = 1000;

            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), b0);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
    		EntityNukeExplosionAdvanced entity0 = new EntityNukeExplosionAdvanced(this.worldObj);
    	    entity0.posX = this.posX;
    	    entity0.posY = this.posY;
    	    entity0.posZ = this.posZ;
    	    entity0.destructionRange = 25;
    	    entity0.speed = 25;
    	    entity0.coefficient = 10.0F;
    	    	
    	    this.worldObj.spawnEntityInWorld(entity0);
        	if(rand.nextInt(100) == 0)
        	{
        		ExplosionParticleB.spawnMush(this.worldObj, (int)this.posX, (int)this.posY - 2, (int)this.posZ);
        	} else {
        		ExplosionParticle.spawnMush(this.worldObj, (int)this.posX, (int)this.posY - 2, (int)this.posZ);
        	}
        }
    }
}
