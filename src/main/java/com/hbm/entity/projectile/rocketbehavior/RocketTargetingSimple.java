package com.hbm.entity.projectile.rocketbehavior;

import com.hbm.entity.projectile.EntityArtilleryRocket;

import net.minecraft.entity.Entity;

/**
 * "Stupid targeting for rockets, they move straight towards the target's current position"
 * @author hbm
 */
public class RocketTargetingSimple implements IRocketTargetingBehavior {

	@Override
	public void recalculateTargetPosition(EntityArtilleryRocket rocket, Entity target) {
		rocket.setTarget(target.posX, target.posY - target.yOffset + target.height * 0.5D, target.posZ);
	}
}
