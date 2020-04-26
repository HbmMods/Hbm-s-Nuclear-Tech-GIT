package com.hbm.entity.effect;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityCloudCustom extends Entity {
	
	public int maxAge = 100;
	public int age;
    public float scale = 0;
    public int r;
    public int g;
    public int b;
    
	public EntityCloudCustom(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(1, 4);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
    	scale = 0;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, Integer.valueOf(0));
		this.dataWatcher.addObject(17, Integer.valueOf(0));
		this.dataWatcher.addObject(18, Integer.valueOf(0));
		this.dataWatcher.addObject(19, Integer.valueOf(0));
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

	public EntityCloudCustom(World p_i1582_1_, int r, int g, int b, int maxAge) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.isImmuneToFire = true;
		this.setMaxAge(maxAge);
		this.setColors(r, g, b);
	}

    @Override
	public void onUpdate() {
        this.age++;
        this.worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, this.posX, this.posY + 200, this.posZ));
        
        if(this.age >= this.getMaxAge())
        {
    		this.age = 0;
        	this.setDead();
        }
        
        this.scale++;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		age = p_70037_1_.getShort("age");
		scale = p_70037_1_.getShort("scale");
		r = p_70037_1_.getByte("r");
		g = p_70037_1_.getByte("g");
		b = p_70037_1_.getByte("b");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("age", (short)age);
		p_70014_1_.setShort("scale", (short)scale);
		p_70014_1_.setByte("r", (byte)r);
		p_70014_1_.setByte("g", (byte)g);
		p_70014_1_.setByte("b", (byte)b);
	}
	
	public void setMaxAge(int i) {
		this.dataWatcher.updateObject(16, Integer.valueOf(i));
	}
	
	public int getMaxAge() {
		return this.dataWatcher.getWatchableObjectInt(16);
	}
	
	public void setColors(int r, int g, int b) {
		this.dataWatcher.updateObject(17, Integer.valueOf(r));
		this.dataWatcher.updateObject(18, Integer.valueOf(g));
		this.dataWatcher.updateObject(19, Integer.valueOf(b));
	}
	
	public int getColors() {
		int color = this.dataWatcher.getWatchableObjectInt(17);
		color = (color << 8) + this.dataWatcher.getWatchableObjectInt(18);
		color = (color << 8) + this.dataWatcher.getWatchableObjectInt(19);
		return color;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }
}
