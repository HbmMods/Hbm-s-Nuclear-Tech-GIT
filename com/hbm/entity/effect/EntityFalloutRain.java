package com.hbm.entity.effect;

import com.hbm.blocks.ModBlocks;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityFalloutRain extends Entity {
	
	public int maxAge = 1000;
	public int age;
    public float scale = 0;

	public EntityFalloutRain(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
    	scale = 0;
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
        	
        	for(int i = 0; i < count; i++) {
	            int x = (int) (posX + rand.nextInt((int) ((getScale() + 1) * 2)) - getScale());
	            int z = (int) (posZ + rand.nextInt((int) ((getScale() + 1) * 2)) - getScale());
	            int y = worldObj.getHeightValue(x, z) - 1;
	            
	            double dist = Math.sqrt(Math.pow(posX - x, 2) + Math.pow(posZ - z, 2));
	            
	            if(dist <= getScale() && worldObj.getBlock(x, y, z) == Blocks.grass)
	            	worldObj.setBlock(x, y, z, ModBlocks.waste_earth);
        	}
        }
        
        if(this.age >= this.maxAge)
        {
    		this.age = 0;
        	this.setDead();
        }
    }

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, Integer.valueOf(0));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		age = p_70037_1_.getShort("age");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("age", (short)age);
		
	}

	public void setScale(int i) {

		this.dataWatcher.updateObject(16, Integer.valueOf(i));
	}

	public int getScale() {

		return this.dataWatcher.getWatchableObjectInt(16);
	}
}
