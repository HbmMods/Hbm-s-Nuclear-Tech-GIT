package com.hbm.entity.grenade;

import com.hbm.items.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityGrenadeBurst extends EntityGrenadeBase {

    public EntityGrenadeBurst(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeBurst(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeBurst(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	
    	if(this.ticksExisted > 20 && !worldObj.isRemote) {
    		this.setDead();
    		
    		for(int i = 0; i < 8; i++) {
    			
    			EntityGrenadeBreach grenade = new EntityGrenadeBreach(worldObj);
    			grenade.posX = posX;
    			grenade.posY = posY;
    			grenade.posZ = posZ;
    			grenade.motionX = rand.nextGaussian() * 0.1D;
    			grenade.motionY = -0.25D;
    			grenade.motionZ = rand.nextGaussian() * 0.1D;
    			
    			worldObj.spawnEntityInWorld(grenade);
    		}
    	}
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            this.setDead();
        	worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(ModItems.grenade_burst)));
        }
    }
}
