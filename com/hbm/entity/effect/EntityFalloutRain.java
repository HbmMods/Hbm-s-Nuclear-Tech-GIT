package com.hbm.entity.effect;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.NukeEnvironmentalEffect;
import com.hbm.lib.Library;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityFalloutRain extends Entity {
	
	public int maxAge = 1000;
	public int age;

	public EntityFalloutRain(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
	}

	public EntityFalloutRain(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.isImmuneToFire = true;
		this.maxAge = maxAge;
	}

    @Override
	public void onUpdate() {
        //super.onUpdate();
        this.age++;

        
        if(!worldObj.isRemote) {
        	
        	int count = (int)(Math.pow(getScale(), 2) * Math.PI / 500);
        	
        	if(count > 50)
        		count = 50;
        	
        	int maxEff = 15;
        	int currEff = 0;
        	
        	for(int i = 0; i < count; i++) {
	            int x = (int) (posX + rand.nextInt((int) ((getScale() + 1) * 2)) - getScale());
	            int z = (int) (posZ + rand.nextInt((int) ((getScale() + 1) * 2)) - getScale());
	            int y = worldObj.getHeightValue(x, z) - 1;
	            
	            double dist = Math.sqrt(Math.pow(posX - x, 2) + Math.pow(posZ - z, 2));
	            
	            if(dist <= getScale()) {
	            	
	            	if(currEff < maxEff && rand.nextInt(30) == 0) {
	            		NukeEnvironmentalEffect.applyStandardAOE(worldObj, x, y, z, 5, 3);
	            		currEff++;
	            		
	            	} else if(worldObj.getBlock(x, y, z) == Blocks.grass) {
	            		worldObj.setBlock(x, y, z, ModBlocks.waste_earth);
	            	}
	            }
        	}
    		
        	List<Object> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(posX - getScale(), 0, posZ - getScale(), posX + getScale(), 256, posZ + getScale()));
    		
        	for(Object o : list) {
        		if(o instanceof EntityLivingBase) {
        			EntityLivingBase entity = (EntityLivingBase) o;
        			
        			if(Math.sqrt(Math.pow(entity.posX - posX, 2) + Math.pow(entity.posZ - posZ, 2)) <= getScale()) {
        				//Library.applyRadiation(entity, 30, 9, 0, 0);
        				
        				entity.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 2));
        			}
        		}
        	}
            
            if(this.age >= this.maxAge)
            {
        		this.age = 0;
            	this.setDead();
            }
            
            //System.out.println(age + " " + maxAge);
        }
    }

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, Integer.valueOf(0));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		age = p_70037_1_.getShort("age");
		setScale(p_70037_1_.getInteger("scale"));
		maxAge = p_70037_1_.getShort("maxAge");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("age", (short)age);
		p_70014_1_.setInteger("scale", getScale());
		p_70014_1_.setShort("maxAge", (short)maxAge);
		
	}

	public void setScale(int i) {

		this.dataWatcher.updateObject(16, Integer.valueOf(i));
	}

	public int getScale() {

		return this.dataWatcher.getWatchableObjectInt(16);
	}
}
