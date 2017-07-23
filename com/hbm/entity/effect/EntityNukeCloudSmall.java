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
    	ring += 0.1F;
    	
        if(age < 150)
        {
        	height = -60F + ((age - 100) * 60 / 50);
        	if(scale < 1.5)
        	{
        		scale += 0.02;
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
    }

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		age = p_70037_1_.getShort("age");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("age", (short)age);
		
	}

}
