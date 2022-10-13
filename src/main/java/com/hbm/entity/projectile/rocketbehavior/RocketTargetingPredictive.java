package com.hbm.entity.projectile.rocketbehavior;

import com.hbm.entity.projectile.EntityArtilleryRocket;

import net.minecraft.entity.Entity;

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

		/* generate averages and predict a new position */
		double predX = target.posX + (motionX / 20D);
		double predY = target.posY - target.yOffset + target.height * 0.5D + (motionY / 20D);
		double predZ = target.posZ + (motionZ / 20D);
		
		rocket.setTarget(predX, predY, predZ);
	}
}
