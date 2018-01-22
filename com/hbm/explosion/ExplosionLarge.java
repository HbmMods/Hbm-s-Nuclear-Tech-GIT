package com.hbm.explosion;

import java.util.List;
import java.util.Random;

import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.projectile.EntityOilSpill;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntityShrapnel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ExplosionLarge {
	
	static Random rand = new Random();

	public static void spawnParticles(World world, double x, double y, double z, int count) {
		
		for(int i = 0; i < count; i++) {
			EntityDSmokeFX fx = new EntityDSmokeFX(world, x, y, z, 0.0, 0.0, 0.0);
			//fx.posX = x;
			//fx.posY = y;
			//fx.posZ = z;
			fx.motionY = rand.nextGaussian() * (1 + (count / 50));
			fx.motionX = rand.nextGaussian() * (1 + (count / 150));
			fx.motionZ = rand.nextGaussian() * (1 + (count / 150));
			world.spawnEntityInWorld(fx);
		}
	}

	public static void spawnShock(World world, double x, double y, double z, int count, double strength) {
		
		Vec3 vec = Vec3.createVectorHelper(strength, 0, 0);
		vec.rotateAroundY(rand.nextInt(360));
		
		for(int i = 0; i < count; i++) {
			EntityDSmokeFX fx = new EntityDSmokeFX(world, x, y, z, 0.0, 0.0, 0.0);
			fx.motionY = 0;
			fx.motionX = vec.xCoord;
			fx.motionZ = vec.zCoord;
			world.spawnEntityInWorld(fx);
			
			vec.rotateAroundY(360 / count);
		}
	}

	public static void spawnBurst(World world, double x, double y, double z, int count, double strength) {
		
		Vec3 vec = Vec3.createVectorHelper(strength, 0, 0);
		vec.rotateAroundY(rand.nextInt(360));
		
		for(int i = 0; i < count; i++) {
			EntityGasFlameFX fx = new EntityGasFlameFX(world, x, y, z, 0.0, 0.0, 0.0);
			fx.motionY = 0;
			fx.motionX = vec.xCoord;
			fx.motionZ = vec.zCoord;
			world.spawnEntityInWorld(fx);
			
			vec.rotateAroundY(360 / count);
		}
	}
	
	public static void spawnRubble(World world, double x, double y, double z, int count) {
		
		for(int i = 0; i < count; i++) {
			EntityRubble rubble = new EntityRubble(world);
			rubble.posX = x;
			rubble.posY = y;
			rubble.posZ = z;
			rubble.motionY = 0.75						* (1 + ((count + rand.nextInt(count * 5))) / 25);
			rubble.motionX = rand.nextGaussian() * 0.75	* (1 + (count / 50));
			rubble.motionZ = rand.nextGaussian() * 0.75	* (1 + (count / 50));
			rubble.getDataWatcher().updateObject(16, (byte)rand.nextInt(7));
			world.spawnEntityInWorld(rubble);
		}
	}
	
	public static void spawnShrapnels(World world, double x, double y, double z, int count) {
		
		for(int i = 0; i < count; i++) {
			EntityShrapnel shrapnel = new EntityShrapnel(world);
			shrapnel.posX = x;
			shrapnel.posY = y;
			shrapnel.posZ = z;
			shrapnel.motionY = ((rand.nextFloat() * 0.5) + 0.5) * (1 + (count / (15 + rand.nextInt(21)))) + (rand.nextFloat() / 50 * count);
			shrapnel.motionX = rand.nextGaussian() * 1	* (1 + (count / 50));
			shrapnel.motionZ = rand.nextGaussian() * 1	* (1 + (count / 50));
			shrapnel.setTrail(rand.nextInt(3) == 0);
			world.spawnEntityInWorld(shrapnel);
		}
	}
	
	public static void spawnTracers(World world, double x, double y, double z, int count) {
		
		for(int i = 0; i < count; i++) {
			EntityShrapnel shrapnel = new EntityShrapnel(world);
			shrapnel.posX = x;
			shrapnel.posY = y;
			shrapnel.posZ = z;
			shrapnel.motionY = ((rand.nextFloat() * 0.5) + 0.5) * (1 + (count / (15 + rand.nextInt(21)))) + (rand.nextFloat() / 50 * count) * 0.25F;
			shrapnel.motionX = rand.nextGaussian() * 1	* (1 + (count / 50)) * 0.25F;
			shrapnel.motionZ = rand.nextGaussian() * 1	* (1 + (count / 50)) * 0.25F;
			shrapnel.setTrail(true);
			world.spawnEntityInWorld(shrapnel);
		}
	}
	
	public static void spawnShrapnelShower(World world, double x, double y, double z, double motionX, double motionY, double motionZ, int count, double deviation) {
		
		for(int i = 0; i < count; i++) {
			EntityShrapnel shrapnel = new EntityShrapnel(world);
			shrapnel.posX = x;
			shrapnel.posY = y;
			shrapnel.posZ = z;
			shrapnel.motionX = motionX + rand.nextGaussian() * deviation;
			shrapnel.motionY = motionY + rand.nextGaussian() * deviation;
			shrapnel.motionZ = motionZ + rand.nextGaussian() * deviation;
			shrapnel.setTrail(rand.nextInt(3) == 0);
			world.spawnEntityInWorld(shrapnel);
		}
	}
	
	public static void spawnMissileDebris(World world, double x, double y, double z, double motionX, double motionY, double motionZ, double deviation, List<ItemStack> debris, ItemStack rareDrop) {
		
		if(debris != null) {
			for(int i = 0; i < debris.size(); i++) {
				if(debris.get(i) != null) {
					int k = rand.nextInt(debris.get(i).stackSize + 1);
					System.out.println(k);
					for(int j = 0; j < k; j++) {
						EntityItem item = new EntityItem(world, x, y, z, new ItemStack(debris.get(i).getItem()));
						item.motionX = (motionX + rand.nextGaussian() * deviation) * 0.85;
						item.motionY = (motionY + rand.nextGaussian() * deviation) * 0.85;
						item.motionZ = (motionZ + rand.nextGaussian() * deviation) * 0.85;
						item.posX = item.posX + item.motionX * 2;
						item.posY = item.posY + item.motionY * 2;
						item.posZ = item.posZ + item.motionZ * 2;
						
						world.spawnEntityInWorld(item);
					}
				}
			}
		}
		
		if(rareDrop != null && rand.nextInt(10) == 0) {
			EntityItem item = new EntityItem(world, x, y, z, rareDrop.copy());
			item.motionX = motionX + rand.nextGaussian() * deviation * 0.1;
			item.motionY = motionY + rand.nextGaussian() * deviation * 0.1;
			item.motionZ = motionZ + rand.nextGaussian() * deviation * 0.1;
			
			world.spawnEntityInWorld(item);
		}
	}
	
	public static void spawnOilSpills(World world, double x, double y, double z, int count) {
		
		for(int i = 0; i < count; i++) {
			EntityOilSpill shrapnel = new EntityOilSpill(world);
			shrapnel.posX = x;
			shrapnel.posY = y;
			shrapnel.posZ = z;
			shrapnel.motionY = ((rand.nextFloat() * 0.5) + 0.5) * (1 + (count / (15 + rand.nextInt(21)))) + (rand.nextFloat() / 50 * count) * 0.25F;
			shrapnel.motionX = rand.nextGaussian() * 1	* (1 + (count / 50)) * 0.15F;
			shrapnel.motionZ = rand.nextGaussian() * 1	* (1 + (count / 50)) * 0.15F;
			world.spawnEntityInWorld(shrapnel);
		}
	}
	
	public static void explode(World world, double x, double y, double z, float strength, boolean cloud, boolean rubble, boolean shrapnel) {
		world.createExplosion(null, x, y, z, strength, true);
		if(cloud)
			spawnParticles(world, x, y, z, cloudFunction((int)strength));
		if(rubble)
			spawnRubble(world, x, y, z, rubbleFunction((int)strength));
		if(shrapnel)
			spawnShrapnels(world, x, y, z, shrapnelFunction((int)strength));
	}
	
	public static void explodeFire(World world, double x, double y, double z, float strength, boolean cloud, boolean rubble, boolean shrapnel) {
		world.newExplosion((Entity)null, (float)x, (float)y, (float)z, strength, true, true);
		if(cloud)
			spawnParticles(world, x, y, z, cloudFunction((int)strength));
		if(rubble)
			spawnRubble(world, x, y, z, rubbleFunction((int)strength));
		if(shrapnel)
			spawnShrapnels(world, x, y, z, shrapnelFunction((int)strength));
	}
	
	public static int cloudFunction(int i) {
		//return (int)(345 * (1 - Math.pow(Math.E, -i/15)) + 15);
		return (int)(545 * (1 - Math.pow(Math.E, -i/15)) + 15);
	}
	
	public static int rubbleFunction(int i) {
		return i/10;
	}
	
	public static int shrapnelFunction(int i) {
		return i/3;
	}
	
}
