package com.hbm.explosion;

import java.util.List;
import java.util.Random;

import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.ParticleUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ExplosionLarge {
	
	static Random rand = new Random();

	@Deprecated public static void spawnParticles(World world, double x, double y, double z, int count) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "smoke");
		data.setString("mode", "cloud");
		data.setInteger("count", count);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z),  new TargetPoint(world.provider.dimensionId, x, y, z, 250));
	}

	public static void spawnParticlesRadial(World world, double x, double y, double z, int count) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "smoke");
		data.setString("mode", "radial");
		data.setInteger("count", count);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z),  new TargetPoint(world.provider.dimensionId, x, y, z, 250));
	}

	public static void spawnShock(World world, double x, double y, double z, int count, double strength) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "smoke");
		data.setString("mode", "shock");
		data.setInteger("count", count);
		data.setDouble("strength", strength);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y + 0.5, z),  new TargetPoint(world.provider.dimensionId, x, y, z, 250));
	}

	public static void spawnBurst(World world, double x, double y, double z, int count, double strength) {
		
		Vec3 vec = Vec3.createVectorHelper(strength, 0, 0);
		vec.rotateAroundY(rand.nextInt(360));
		
		for(int i = 0; i < count; i++) {
			ParticleUtil.spawnGasFlame(world, x, y, z, vec.xCoord, 0.0, vec.zCoord);
			
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
			rubble.setMetaBasedOnBlock(Blocks.stone, 0);
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
					for(int j = 0; j < k; j++) {
						EntityItem item = new EntityItem(world, x, y, z, debris.get(i).copy());
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
	
	@Deprecated public static void explode(World world, double x, double y, double z, float strength, boolean cloud, boolean rubble, boolean shrapnel, Entity exploder) {
		world.createExplosion(exploder, x, y, z, strength, true);
		if(cloud)
			spawnParticles(world, x, y, z, cloudFunction((int)strength));
		if(rubble)
			spawnRubble(world, x, y, z, rubbleFunction((int)strength));
		if(shrapnel)
			spawnShrapnels(world, x, y, z, shrapnelFunction((int)strength));
	}
	
	@Deprecated public static void explode(World world, double x, double y, double z, float strength, boolean cloud, boolean rubble, boolean shrapnel) {
		world.createExplosion(null, x, y, z, strength, true);
		if(cloud)
			spawnParticles(world, x, y, z, cloudFunction((int)strength));
		if(rubble)
			spawnRubble(world, x, y, z, rubbleFunction((int)strength));
		if(shrapnel)
			spawnShrapnels(world, x, y, z, shrapnelFunction((int)strength));
	}
	
	@Deprecated public static void explodeFire(World world, double x, double y, double z, float strength, boolean cloud, boolean rubble, boolean shrapnel) {
		world.newExplosion((Entity)null, (float)x, (float)y, (float)z, strength, true, true);
		if(cloud)
			spawnParticles(world, x, y, z, cloudFunction((int)strength));
		if(rubble)
			spawnRubble(world, x, y, z, rubbleFunction((int)strength));
		if(shrapnel)
			spawnShrapnels(world, x, y, z, shrapnelFunction((int)strength));
	}
	
	public static void buster(World world, double x, double y, double z, Vec3 vector, float strength, float depth) {
		
		vector = vector.normalize();
		
		for(int i = 0; i < depth; i += 2) {
			
			world.createExplosion((Entity)null, x + vector.xCoord * i, y + vector.yCoord * i, z + vector.zCoord * i, strength, true);
		}
	}
	
	public static void jolt(World world, double posX, double posY, double posZ, double strength, int count, double vel) {
		
		for(int j = 0; j < count; j++) {
			
			double phi = rand.nextDouble() * (Math.PI * 2);
			double costheta = rand.nextDouble() * 2 - 1;
			double theta = Math.acos(costheta);
			double x = Math.sin( theta) * Math.cos( phi );
			double y = Math.sin( theta) * Math.sin( phi );
			double z = Math.cos( theta );
				
			Vec3 vec = Vec3.createVectorHelper(x, y, z);
				
			for(int i = 0; i < strength; i ++) {
				double x0 = posX + (vec.xCoord * i);
				double y0 = posY + (vec.yCoord * i);
				double z0 = posZ + (vec.zCoord * i);
					
				if(!world.isRemote) {
					if(world.getBlock((int)x0, (int)y0, (int)z0).getMaterial().isLiquid()) {
						world.setBlock((int)x0, (int)y0, (int)z0, Blocks.air);
					}
					
					if(world.getBlock((int)x0, (int)y0, (int)z0) != Blocks.air) {
						
						if(world.getBlock((int)x0, (int)y0, (int)z0).getExplosionResistance(null, world, (int)x0, (int)y0, (int)z0, posX, posY, posZ) > 70)
							continue;
			            
			            EntityRubble rubble = new EntityRubble(world);
						rubble.posX = x0 + 0.5F;
						rubble.posY = y0 + 0.5F;
						rubble.posZ = z0 + 0.5F;
						rubble.setMetaBasedOnBlock(world.getBlock((int)x0, (int)y0, (int)z0), world.getBlockMetadata((int)x0, (int)y0, (int)z0));
						
						Vec3 vec4 = Vec3.createVectorHelper(posX - rubble.posX, posY - rubble.posY, posZ - rubble.posZ);
						vec4.normalize();

						rubble.motionX = vec4.xCoord * vel;
						rubble.motionY = vec4.yCoord * vel;
						rubble.motionZ = vec4.zCoord * vel;
						
						world.spawnEntityInWorld(rubble);
					
						world.setBlock((int)x0, (int)y0, (int)z0, Blocks.air);
						break;
					}
				}
			}
		}
	}
	
	public static int cloudFunction(int i) {
		return (int)(850 * (1 - Math.pow(Math.E, -i/15)) + 15);
	}
	
	public static int rubbleFunction(int i) {
		return i/10;
	}
	
	public static int shrapnelFunction(int i) {
		return i/3;
	}
	
}
