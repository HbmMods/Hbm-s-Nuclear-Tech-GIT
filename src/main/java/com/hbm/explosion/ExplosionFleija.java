package com.hbm.explosion;

import com.hbm.blocks.generic.DecoBlockAlt;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ExplosionFleija
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
	public float explosionCoefficient2 = 1.0F;
	
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
		nbt.setFloat(name + "explosionCoefficient2", explosionCoefficient2);
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
		explosionCoefficient2 = nbt.getFloat(name + "explosionCoefficient2");
	}
	
	public ExplosionFleija(int x, int y, int z, World world, int rad, float coefficient, float coefficient2)
	{
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.worldObj = world;
		
		this.radius = rad;
		this.radius2 = this.radius * this.radius;

		this.explosionCoefficient = coefficient;
		this.explosionCoefficient2 = coefficient2;
		
		this.nlimit = this.radius2 * 4;
	}
	
	public boolean update()
	{
		breakColumn(this.lastposX, this.lastposZ);
		this.shell = (int) Math.floor((Math.sqrt(n) + 1) / 2);
		int shell2 = this.shell * 2;
		this.leg = (int) Math.floor((this.n - (shell2 - 1) * (shell2 - 1)) / shell2);
		this.element = (this.n - (shell2 - 1) * (shell2 - 1)) - shell2 * this.leg - this.shell + 1;
		this.lastposX = this.leg == 0 ? this.shell : this.leg == 1 ? -this.element : this.leg == 2 ? -this.shell : this.element;
		this.lastposZ = this.leg == 0 ? this.element : this.leg == 1 ? this.shell : this.leg == 2 ? -this.element : -this.shell;
		this.n++;
		return this.n > this.nlimit;
	}

	private void breakColumn(int x, int z)
	{
		int dist = this.radius2 - (x * x + z * z);
		if (dist > 0)
		{
			dist = (int) Math.sqrt(dist);
			for (int y = (int)(dist / this.explosionCoefficient2); y > -dist / this.explosionCoefficient; y--)
			{
				if(this.posY + y > 0 && !(this.worldObj.getBlock(this.posX+x, this.posY+y, this.posZ+z) instanceof DecoBlockAlt))this.worldObj.setBlock(this.posX+x, this.posY+y, this.posZ+z, Blocks.air);
			}
		}
	}
}
