package com.hbm.entity.effect;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNukeCloudSmall extends Entity {
	
	public int maxAge = 1000;
	public int age;
    public float scale = 0;
    public float ring = 0;
    public float height = 0;

	public EntityNukeCloudSmall(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(1, 80);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
    	scale = 0;
    	ring = 0;
    	height = 0;
	}

    @Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_)
    {
        return 15728880;
    }

    @Override
	public float getBrightness(float p_70013_1_)
    {
        return 1.0F;
    }

	public EntityNukeCloudSmall(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.isImmuneToFire = true;
		this.maxAge = maxAge;
	}

    @Override
	public void onUpdate() {
        //super.onUpdate();
        this.age++;
        this.worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, this.posX, this.posY + 200, this.posZ));
        
        if(this.age >= this.maxAge)
        {
    		this.age = 0;
        	this.setDead();
        }
    	ring += 0.03F;
    	
        if(age < 150)
        {
        	height = -60F + ((age - 100) * 60 / 50);
        	if(scale < 1.5)
        	{
        		scale += 0.006f;
        	}
        }
        
        if(age > 100)
        {
        	if(scale < 1.5)
        	{
        		scale += 0.02;
        	}
        } else {
        	scale = 0;
        }

        this.dataWatcher.updateObject(16, (short)maxAge);
        this.dataWatcher.updateObject(17, (short)age);
        this.dataWatcher.updateObject(18, (short)scale); 
        this.dataWatcher.updateObject(19, (short)ring);
        this.dataWatcher.updateObject(20, (short)height);
    }

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, (short)maxAge);
		this.dataWatcher.addObject(17, (short)age);
		this.dataWatcher.addObject(18, (short)scale);
		this.dataWatcher.addObject(19, (short)ring);
		this.dataWatcher.addObject(20, (short)height);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		maxAge = p_70037_1_.getShort("maxAge");
		age = p_70037_1_.getShort("age");
		scale = p_70037_1_.getShort("scale");
		ring = p_70037_1_.getShort("ring");
		height = p_70037_1_.getShort("height");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("maxAge", (short)maxAge);
		p_70014_1_.setShort("age", (short)age);
		p_70014_1_.setShort("scale", (short)scale);
		p_70014_1_.setShort("ring", (short)ring);
		p_70014_1_.setShort("height", (short)height);
		
	}

}
