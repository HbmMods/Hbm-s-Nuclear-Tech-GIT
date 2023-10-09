package com.hbm.explosion;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ExplosionNukeAdvanced
{
	public int posX;
	public int posY;
	public int posZ;
	public int lastposX = 0;
	public int lastposZ = 0;
	public int radius;
	public int radius2;
	public World worldObj;
	private int n = 1;
	private int nlimit;
	private int shell;
	private int leg;
	private int element;
	public float explosionCoefficient = 1.0F;
	public int type = 0;
	
	public void saveToNbt(NBTTagCompound nbt, String name) {
		nbt.setInteger(name + "posX", posX);
		nbt.setInteger(name + "posY", posY);
		nbt.setInteger(name + "posZ", posZ);
		nbt.setInteger(name + "lastposX", lastposX);
		nbt.setInteger(name + "lastposZ", lastposZ);
		nbt.setInteger(name + "radius", radius);
		nbt.setInteger(name + "radius2", radius2);
		nbt.setInteger(name + "n", n);
		nbt.setInteger(name + "nlimit", nlimit);
		nbt.setInteger(name + "shell", shell);
		nbt.setInteger(name + "leg", leg);
		nbt.setInteger(name + "element", element);
		nbt.setFloat(name + "explosionCoefficient", explosionCoefficient);
		nbt.setInteger(name + "type", type);
	}
	
	public void readFromNbt(NBTTagCompound nbt, String name) {
		posX = nbt.getInteger(name + "posX");
		posY = nbt.getInteger(name + "posY");
		posZ = nbt.getInteger(name + "posZ");
		lastposX = nbt.getInteger(name + "lastposX");
		lastposZ = nbt.getInteger(name + "lastposZ");
		radius = nbt.getInteger(name + "radius");
		radius2 = nbt.getInteger(name + "radius2");
		n = nbt.getInteger(name + "n");
		nlimit = nbt.getInteger(name + "nlimit");
		shell = nbt.getInteger(name + "shell");
		leg = nbt.getInteger(name + "leg");
		element = nbt.getInteger(name + "element");
		explosionCoefficient = nbt.getFloat(name + "explosionCoefficient");
		type = nbt.getInteger(name + "type");
	}
	
	public ExplosionNukeAdvanced(int x, int y, int z, World world, int rad, float coefficient, int typ)
	{
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.worldObj = world;
		
		this.radius = rad;
		this.radius2 = this.radius * this.radius;

		this.explosionCoefficient = Math.min(Math.max((rad + coefficient * (y - 60))/(coefficient*rad), 1/coefficient),1.0f);	//scale the coefficient depending on detonation height
		this.type = typ;
		
		this.nlimit = this.radius2 * 4; //How many total columns should be broken (radius ^ 2 is one quadrant, there are 4 quadrants)
	}
	
	public boolean update()
	{
		switch(this.type)
		{
		case 0:
			breakColumn(this.lastposX, this.lastposZ); break;
		case 1:
			vapor(this.lastposX, this.lastposZ); break;
		case 2:
			waste(this.lastposX, this.lastposZ); break;
		}
		this.shell = (int) Math.floor((Math.sqrt(n) + 1) / 2); //crazy stuff I can't explain
		int shell2 = this.shell * 2;
		this.leg = (int) Math.floor((this.n - (shell2 - 1) * (shell2 - 1)) / shell2);
		this.element = (this.n - (shell2 - 1) * (shell2 - 1)) - shell2 * this.leg - this.shell + 1;
		this.lastposX = this.leg == 0 ? this.shell : this.leg == 1 ? -this.element : this.leg == 2 ? -this.shell : this.element;
		this.lastposZ = this.leg == 0 ? this.element : this.leg == 1 ? this.shell : this.leg == 2 ? -this.element : -this.shell;
		this.n++;
		return this.n > this.nlimit; //return whether we are done or not
	}

	private void breakColumn(int x, int z)
	{
		int dist = this.radius2 - (x * x + z * z); //we have two sides of the triangle (hypotenuse is radius, one leg is (x*x+z*z)) this calculates the third one
		if (dist > 0) //check if any blocks have to be broken here
		{
			dist = (int) Math.sqrt(dist); //calculate sphere height at this (x,z) coordinate
			for (int y = dist; y > -dist * this.explosionCoefficient; y--) //go from top to bottom to favor light updates
			{
				if(y<8){//only spare blocks that are mostly below epicenter
					y-= ExplosionNukeGeneric.destruction(this.worldObj, this.posX + x, this.posY + y, this.posZ + z);//spare blocks below
				}else{//don't spare blocks above epicenter
					ExplosionNukeGeneric.destruction(this.worldObj, this.posX + x, this.posY + y, this.posZ + z);
				}
			}
		}
	}

	private void vapor(int x, int z)
	{
		int dist = this.radius2 - (x * x + z * z);
		if (dist > 0)
		{
			dist = (int) Math.sqrt(dist);
			//int dist0 = (int)Math.sqrt(this.radius2*0.15f - (x * x + z * z));
			for (int y = dist; y > -dist * this.explosionCoefficient; y--)
			{
				y-=ExplosionNukeGeneric.vaporDest(this.worldObj, this.posX + x, this.posY + y, this.posZ + z);
				/*
				if(dist0>0){//skip blocks already in the destruction zone: we will 
					if(y>=dist0 || y<=-dist0*this.explosionCoefficient){
						y-=ExplosionNukeGeneric.vaporDest(this.worldObj, this.posX + x, this.posY + y, this.posZ + z);
					}
				}else{
					y-=ExplosionNukeGeneric.vaporDest(this.worldObj, this.posX + x, this.posY + y, this.posZ + z);
				}*/
			}
		}
	}

	private void waste(int x, int z)
	{
		int dist = this.radius2 - (x * x + z * z);
		if (dist > 0)
		{
			dist = (int) Math.sqrt(dist);
			for (int y = dist; y > -dist * this.explosionCoefficient; y--)
			{
				if(radius >= 95)
					ExplosionNukeGeneric.wasteDest(this.worldObj, this.posX + x, this.posY + y, this.posZ + z);
				else
					ExplosionNukeGeneric.wasteDestNoSchrab(this.worldObj, this.posX + x, this.posY + y, this.posZ + z);
			}
		}
	}
}
