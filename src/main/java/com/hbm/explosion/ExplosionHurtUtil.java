package com.hbm.explosion;

import java.util.List;

import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ExplosionHurtUtil {
	
	/**
	 * Adds radiation to entities in an AoE
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param outer The least amount of radiation received on the very edge of the AoE
	 * @param inner The greatest amount of radiation received on the very center of the AoE
	 * @param radius
	 */
	public static void doRadiation(World world, double x, double y, double z, float outer, float inner, double radius) {
		
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
		
		for(EntityLivingBase entity : entities) {
			
			Vec3 vec = Vec3.createVectorHelper(x - entity.posX, y - entity.posY, z - entity.posZ);
			
			double dist = vec.lengthVector();
			
			if(dist > radius)
				continue;
			
			double interpolation = 1 - (dist / radius);
			float rad = (float) (outer + (inner - outer) * interpolation);
			
			ContaminationUtil.applyRadData(entity, rad);
		}
	}
	public static void doDigamma(World world, double x, double y, double z, float outer, float inner, double radius)
	{
		List<EntityLivingBase> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
		
		for (EntityLivingBase entity : entityList)
		{
			Vec3 vec = Vec3.createVectorHelper(x - entity.posX, y - entity.posY, z - entity.posZ);
			double dist = vec.lengthVector();
			if (dist > radius)
				continue;
			
			double interpolation = 1 - (dist / radius);
			float rad = (float) (outer + (inner - outer) * interpolation);
			int pDuration = (int) (100 * interpolation);
			if (pDuration < 20)
				pDuration = 20;
			ContaminationUtil.applyDigammaData(entity, rad);
			entity.addPotionEffect(new PotionEffect(HbmPotion.hollow.id, (int) pDuration, 1));
		}
	}
	public static void doStun(World world, double x, double y, double z, double radius)
	{
		List<EntityLivingBase> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
		
		for (EntityLivingBase entity : entityList)
		{
			Vec3 vec = Vec3.createVectorHelper(x - entity.posX, y - entity.posY, z - entity.posZ);
			double dist = vec.lengthVector();
			if (dist > radius)
				continue;
			
			//double interpolation = 1 - (dist / radius);
			//float rad = (float) (outer + (inner - outer) * interpolation);
			//entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 20));
			//entity.addPotionEffect(new PotionEffect(Potion.jump.id, 200, 128));
			entity.addPotionEffect(new PotionEffect(HbmPotion.paralysis.id, 200, 0));
		}
	}
}
