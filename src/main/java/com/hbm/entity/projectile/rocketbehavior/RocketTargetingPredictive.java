package com.hbm.entity.projectile.rocketbehavior;

import com.hbm.entity.projectile.EntityArtilleryRocket;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

/**
 * Basic implementation of predictive targeting.
 * My current best guess for "smart" targeting
 * Keeps a buffer of the last second's velocity values and generates a predicted target based on the average speed.
 * @author hbm
 */
public class RocketTargetingPredictive implements IRocketTargetingBehavior {
	
	/*
	 * unlikely to work for things that move extremely erratically and quickly,
	 * but there's barely any  cases where that would apply.
	 * a single second of prediction is good enough at long distances where there's
	 * still room for error, like player vehicles or ICBMs.
	 */
	private double[][] targetMotion = new double[20][3];

	@Override
	public void recalculateTargetPosition(EntityArtilleryRocket rocket, Entity target) {

		Vec3 speed = Vec3.createVectorHelper(rocket.motionX, rocket.motionY, rocket.motionZ);
		Vec3 delta = Vec3.createVectorHelper(target.posX - rocket.posX, target.posY - rocket.posY, target.posZ - rocket.posZ);
		double eta = delta.lengthVector() - speed.lengthVector();
		
		/* initialize with the values we already know */
		double motionX = target.motionX;
		double motionY = target.motionY;
		double motionZ = target.motionZ;
		
		/* shift the buffer and add the older values */
		for(int i = 1; i < 20; i++) {
			targetMotion[i - 1] = targetMotion[i];
			motionX += targetMotion[i][0];
			motionY += targetMotion[i][1];
			motionZ += targetMotion[i][2];
		}

		/* push the new values to the buffer for future use */
		targetMotion[19][0] = target.motionX;
		targetMotion[19][1] = target.motionY;
		targetMotion[19][2] = target.motionZ;
		
		if(eta <= 1) {
			rocket.setTarget(target.posX, target.posY - target.yOffset + target.height * 0.5D, target.posZ);
			return;
		}

		/* generate averages and predict a new position */
		double predX = target.posX + (motionX / 20D) * eta;
		double predY = target.posY - target.yOffset + target.height * 0.5D + (motionY / 20D) * eta;
		double predZ = target.posZ + (motionZ / 20D) * eta;
		
		rocket.setTarget(predX, predY, predZ);
	}
}
