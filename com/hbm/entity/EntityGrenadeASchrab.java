package com.hbm.entity;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGrenadeASchrab extends EntityThrowable
{
    private static final String __OBFID = "CL_00001722";

    public EntityGrenadeASchrab(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeASchrab(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeASchrab(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
	protected void onImpact(MovingObjectPosition p_70184_1_)
    {
        if (p_70184_1_.entityHit != null)
        {
            byte b0 = 0;

            if (p_70184_1_.entityHit instanceof EntityBlaze)
            {
                b0 = 3;
            }

            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), b0);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
			if (!this.worldObj.isRemote) {
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ,
						"random.explode", 100.0f, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);

				EntityNukeExplosionAdvanced entity = new EntityNukeExplosionAdvanced(this.worldObj);
				entity.posX = this.posX;
				entity.posY = this.posY;
				entity.posZ = this.posZ;
				entity.destructionRange = MainRegistry.aSchrabRadius;
				entity.speed = 25;
				entity.coefficient = 1.0F;
				entity.waste = false;

				this.worldObj.spawnEntityInWorld(entity);
	    		
	    		EntityCloudFleija cloud = new EntityCloudFleija(this.worldObj, MainRegistry.aSchrabRadius);
	    		cloud.posX = this.posX;
	    		cloud.posY = this.posY;
	    		cloud.posZ = this.posZ;
	    		this.worldObj.spawnEntityInWorld(cloud);
			}
        }
    }
}
