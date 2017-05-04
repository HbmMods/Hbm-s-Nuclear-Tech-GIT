package com.hbm.entity.projectile;

import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityShrapnel extends EntityThrowable {

    public EntityShrapnel(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityShrapnel(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public void entityInit() {
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    public EntityShrapnel(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	if(!worldObj.isRemote)
    		if(this.dataWatcher.getWatchableObjectByte(16) == 1) {
    			worldObj.spawnEntityInWorld(new EntitySSmokeFX(worldObj, this.posX, this.posY - 0.5, this.posZ, 0.0, 0.0, 0.0));
    			worldObj.spawnEntityInWorld(new EntitySSmokeFX(worldObj, this.posX - this.motionX, this.posY - 0.5 - this.motionY, this.posZ - this.motionZ, 0.0, 0.0, 0.0));
    		}
    }

    @Override
	protected void onImpact(MovingObjectPosition p_70184_1_)
    {
        if (p_70184_1_.entityHit != null)
        {
            byte b0 = 15;

            p_70184_1_.entityHit.attackEntityFrom(ModDamageSource.shrapnel, b0);
        }

        if(this.ticksExisted > 5) {
        	this.setDead();
        	if(!this.worldObj.isRemote)
        		worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0.1F, true);
        }
    }
    
    public void setTrail(boolean b) {
        	this.dataWatcher.updateObject(16, (byte)(b ? 1 : 0));
    }
}
