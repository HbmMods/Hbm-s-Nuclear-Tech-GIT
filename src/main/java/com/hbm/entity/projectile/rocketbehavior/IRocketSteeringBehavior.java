package com.hbm.entity.projectile.rocketbehavior;

import com.hbm.entity.projectile.EntityArtilleryRocket;

public interface IRocketSteeringBehavior {
	
	/** Modifies the motion to steer towards the set target. */
	public void adjustCourse(EntityArtilleryRocket rocket, double speed, double turnSpeed);
}
