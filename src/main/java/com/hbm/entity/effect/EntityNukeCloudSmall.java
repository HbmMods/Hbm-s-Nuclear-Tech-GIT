package com.hbm.entity.effect;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityNukeCloudSmall extends Entity {
	
	public int maxAge = 1000;
	public int age;
	
	public static int cloudletLife = 50;
	public ArrayList<Cloudlet> cloudlets = new ArrayList();

	public EntityNukeCloudSmall(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
		this.noClip = true;
	}

	public EntityNukeCloudSmall(World p_i1582_1_, int maxAge, float scale) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.isImmuneToFire = true;
		this.maxAge = maxAge;
		this.noClip = true;
		this.dataWatcher.updateObject(18, scale);
	}

    @Override
	public void onUpdate() {
    	
        this.age++;
        
        this.worldObj.lastLightningBolt = 2;
        
        if(this.age >= this.maxAge)
        {
    		this.age = 0;
        	this.setDead();
        }
        
        int cloudCount = age * 3;
        
        Vec3 vec = Vec3.createVectorHelper(age * 2, 0, 0);
        
        int toRem = 0;
        
        for(int i = 0; i < this.cloudlets.size(); i++) {
        	
        	if(age > cloudlets.get(i).age + cloudletLife)
        		toRem = i;
        	else
        		break;
        }
        
        for(int i = 0; i < toRem; i++)
        	this.cloudlets.remove(0);
        
        if(age < 200) {
	        for(int i = 0; i < cloudCount; i++) {
	        	vec.rotateAroundY((float)(Math.PI * 2 * worldObj.rand.nextDouble()));
	        	
	        	this.cloudlets.add(new Cloudlet(vec.xCoord, worldObj.getHeightValue((int) (vec.xCoord + posX), (int) (vec.zCoord + posZ)), vec.zCoord, age));
	        }
        }

        this.dataWatcher.updateObject(16, (short)maxAge);
        this.dataWatcher.updateObject(17, (short)age);
    }

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, (short)maxAge);
		this.dataWatcher.addObject(17, (short)age);
		this.dataWatcher.addObject(18, 1.0F);
        this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		maxAge = p_70037_1_.getShort("maxAge");
		age = p_70037_1_.getShort("age");
		this.dataWatcher.updateObject(18, p_70037_1_.getFloat("scale"));
		this.dataWatcher.updateObject(19, p_70037_1_.getByte("type"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("maxAge", (short)maxAge);
		p_70014_1_.setShort("age", (short)age);
		p_70014_1_.setFloat("scale", this.dataWatcher.getWatchableObjectFloat(18));
		p_70014_1_.setByte("type", this.dataWatcher.getWatchableObjectByte(19));
		
	}
	
	public static EntityNukeCloudSmall statFac(World world, double x, double y, double z, float radius) {
		
		EntityNukeCloudSmall cloud = new EntityNukeCloudSmall(world, (int)radius * 5, radius * 0.005F);
		cloud.posX = x;
		cloud.posY = y;
		cloud.posZ = z;
		cloud.dataWatcher.updateObject(19, (byte)0);
		
		return cloud;
	}
	
	public static EntityNukeCloudSmall statFacBale(World world, double x, double y, double z, float radius, int maxAge) {
		
		EntityNukeCloudSmall cloud = new EntityNukeCloudSmall(world, (int)radius * 5, radius * 0.005F);
		cloud.posX = x;
		cloud.posY = y;
		cloud.posZ = z;
		cloud.dataWatcher.updateObject(19, (byte)1);
		
		return cloud;
	}
	
	public static EntityNukeCloudSmall statFacAnti(World world, double x, double y, double z, float radius, int maxAge) {
		
		EntityNukeCloudSmall cloud = new EntityNukeCloudSmall(world, (int)radius * 5, radius * 0.005F);
		cloud.posX = x;
		cloud.posY = y;
		cloud.posZ = z;
		cloud.dataWatcher.updateObject(19, (byte)2);
		
		return cloud;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return true;
    }
    
    public static class Cloudlet {
    	
    	public double posX;
    	public double posY;
    	public double posZ;
    	public int age;
    	
    	public Cloudlet(double posX, double posY, double posZ, int age) {
    		this.posX = posX;
    		this.posY = posY;
    		this.posZ = posZ;
    		this.age = age;
    	}
    }

}
