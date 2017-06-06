package com.hbm.entity.grenade;

import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGrenadeNuclear extends EntityGrenadeBase
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

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            this.setDead();
    		EntityNukeExplosionAdvanced entity0 = new EntityNukeExplosionAdvanced(this.worldObj);
    	    entity0.posX = this.posX;
    	    entity0.posY = this.posY;
    	    entity0.posZ = this.posZ;
    	    entity0.destructionRange = MainRegistry.nukaRadius;
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
