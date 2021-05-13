package com.hbm.explosion;

import java.util.List;

import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.entity.EntityLivingBase;
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
			
			ContaminationUtil.contaminate(entity, HazardType.RADIATION, ContaminationType.CREATIVE, rad);
		}
	}

}
