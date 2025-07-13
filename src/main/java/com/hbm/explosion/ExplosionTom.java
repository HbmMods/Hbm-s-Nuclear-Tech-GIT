package com.hbm.explosion;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ExplosionTom {
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
	}

	public ExplosionTom(int x, int y, int z, World world, int rad) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;

		this.worldObj = world;

		this.radius = rad;
		this.radius2 = this.radius * this.radius;

		this.nlimit = this.radius2 * 4;
	}

	public boolean update() {
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

	private void breakColumn(int x, int z) {
		int dist = this.radius2 - (x * x + z * z);

		if(dist > 0) {
			int pX = posX + x;
			int pZ = posZ + z;
			double X = Math.pow((this.posX - pX), 2);
			double Z = Math.pow((this.posZ - pZ), 2);
			double distance = MathHelper.sqrt_double(X + Z); // Distance calculations used for crater rim stuff

			int y = 256;
			int terrain = 63;

			double cA = (terrain - Math.pow(Math.E, -Math.pow(Math.sqrt(x * x + z * z), 2) / 40000) * 13) + worldObj.rand.nextInt(2); // Basic crater bowl shape
			double cB = cA + Math.pow(Math.E, -Math.pow(Math.sqrt(x * x + z * z) - 200, 2) / 400) * 13 ;// Crater peak ring
			int craterFloor = (int) (cB + Math.pow(Math.E, -Math.pow(Math.sqrt(x * x + z * z) - 500, 2) / 2000) * 37); // Crater rim
			for(int i = 256; i > 0; i--) {
				if(i == craterFloor || worldObj.getBlock(pX, i, pZ) != Blocks.air) {
					y = i;
					break;
				}
			}
			int height = terrain - 14;
			int offset = 20;
			int threshold = (int) ((float) Math.sqrt(x * x + z * z) * (float) (height + offset) / (float) this.radius) + worldObj.rand.nextInt(2) - offset;

			while(y > threshold) {

				if(y == 0) break;
				
				if(y <= craterFloor) {

					if(worldObj.rand.nextInt(499) < 1) {
						worldObj.setBlock(pX, y, pZ, ModBlocks.ore_tektite_osmiridium, 0, 2);
					} else {
						worldObj.setBlock(pX, y, pZ, ModBlocks.tektite, 0, 2);
					}

				} else {
					if(y > terrain + 1) {
						if(distance < 500) // used so that old terrain inside crater rim is destroyed, while rim material "floods" terrain outside.
						{
							for(int i = -2; i < 3; i++) {
								for(int j = -2; j < 3; j++) {
									for(int k = -2; k < 3; k++) {
										if(worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial() == Material.water || worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial() == Material.ice || worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial() == Material.snow || worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial().getCanBurn()) {
											worldObj.setBlockToAir(pX + i, y + j, pZ + k);
											worldObj.setBlockToAir(pX, y, pZ);
										}
									}
								}
							}
							worldObj.setBlock(pX, y, pZ, Blocks.air, 0, 2);
						}
					} else {
						for(int i = -2; i < 3; i++) {
							for(int j = -2; j < 3; j++) {
								for(int k = -2; k < 3; k++) {
									if(worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial() == Material.water || worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial() == Material.ice || worldObj.getBlock(pX + i, y, pZ + k) == Blocks.air) {
										worldObj.setBlock(pX + i, y, pZ + k, Blocks.lava, 0, 2);
										worldObj.setBlock(pX, y, pZ, Blocks.lava, 0, 2);
									}
								}
							}
						}
						worldObj.setBlock(pX, y, pZ, Blocks.lava, 0, 2);
					}

				}
				y--;
			}
		}
	}
}