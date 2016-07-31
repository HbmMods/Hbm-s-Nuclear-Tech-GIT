package com.hbm.explosion;

import com.hbm.blocks.DecoBlockAlt;
import net.minecraft.init.Blocks;
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
				if(!(this.worldObj.getBlock(this.posX+x, this.posY+y, this.posZ+z) == Blocks.bedrock && this.posY+y <= 0) && !(this.worldObj.getBlock(this.posX+x, this.posY+y, this.posZ+z) instanceof DecoBlockAlt))this.worldObj.setBlock(this.posX+x, this.posY+y, this.posZ+z, Blocks.air);
			}
		}
	}
}
