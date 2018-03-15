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

	public EntityNukeCloudSmall(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(1, 80);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
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

	public EntityNukeCloudSmall(World p_i1582_1_, int maxAge, float scale) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.isImmuneToFire = true;
		this.maxAge = maxAge;
		this.dataWatcher.updateObject(18, scale);
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

        this.dataWatcher.updateObject(16, (short)maxAge);
        this.dataWatcher.updateObject(17, (short)age);
    }

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, (short)maxAge);
		this.dataWatcher.addObject(17, (short)age);
		this.dataWatcher.addObject(18, 1.0F);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		maxAge = p_70037_1_.getShort("maxAge");
		age = p_70037_1_.getShort("age");
		this.dataWatcher.updateObject(18, p_70037_1_.getFloat("scale"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("maxAge", (short)maxAge);
		p_70014_1_.setShort("age", (short)age);
		p_70014_1_.setFloat("scale", this.dataWatcher.getWatchableObjectFloat(18));
		
	}

}
