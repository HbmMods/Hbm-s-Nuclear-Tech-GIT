package com.hbm.entity.projectile;

import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
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

public class EntityRubble extends EntityThrowable {

    public EntityRubble(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityRubble(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public void entityInit() {
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    public EntityRubble(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
	protected void onImpact(MovingObjectPosition p_70184_1_)
    {
        if (p_70184_1_.entityHit != null)
        {
            byte b0 = 15;

            p_70184_1_.entityHit.attackEntityFrom(ModDamageSource.rubble, b0);
        }

        if(this.ticksExisted > 5) {
        	this.setDead();
        	if(!this.worldObj.isRemote)
        		worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0.1F, true);
        }
    }
    
    public void setMetaBasedOnMat(Material mat) {
    	if(mat == Material.anvil || mat == Material.iron)
        	this.dataWatcher.updateObject(16, (byte)0);
    	else if(mat == Material.rock || mat == Material.piston || mat == Material.redstoneLight)
        	this.dataWatcher.updateObject(16, (byte)1);
    	else if(mat == Material.cactus || mat == Material.coral || mat == Material.gourd || mat == Material.leaves || mat == Material.plants || mat == Material.sponge)
        	this.dataWatcher.updateObject(16, (byte)2);
    	else if(mat == Material.clay || mat == Material.sand)
        	this.dataWatcher.updateObject(16, (byte)3);
    	else if(mat == Material.ground || mat == Material.grass)
        	this.dataWatcher.updateObject(16, (byte)4);
    	else if(mat == Material.wood)
        	this.dataWatcher.updateObject(16, (byte)5);
    	else
        	this.dataWatcher.updateObject(16, (byte)6);
    }
}
