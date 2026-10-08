package com.hbm.entity.projectile.rocketbehavior;

import com.hbm.entity.projectile.EntityArtilleryRocket;

import net.minecraft.util.Vec3;

public class RocketSteeringBallisticArc implements IRocketSteeringBehavior {

	@Override
	public void adjustCourse(EntityArtilleryRocket rocket, double speed, double maxTurn) {
		
		double turnSpeed = 45;
		
		Vec3 direction = Vec3.createVectorHelper(rocket.motionX, rocket.motionY, rocket.motionZ).normalize();
		double horizontalMomentum = Math.sqrt(rocket.motionX * rocket.motionX + rocket.motionZ * rocket.motionZ);
		Vec3 targetPos = rocket.getLastTarget();
		double deltaX = targetPos.xCoord - rocket.posX;
		double deltaZ = targetPos.zCoord - rocket.posZ;
		double horizontalDelta = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		double stepsRequired = horizontalDelta / horizontalMomentum;
		Vec3 target = Vec3.createVectorHelper(targetPos.xCoord - rocket.posX, targetPos.yCoord - rocket.posY, targetPos.zCoord - rocket.posZ).normalize();
		
		/* the entity's angles lack precision and i lack the nerve to figure out how they're oriented */
		double rocketYaw = yaw(direction);
		double rocketPitch = pitch(direction);
		double targetYaw = yaw(target);
		double targetPitch = pitch(target);
		
		boolean debug = false;

		if(debug) {
			System.out.println("=== INITIAL ===");
			System.out.println("Rocket Yaw: " + rocketYaw);
			System.out.println("Rocket Pitch: " + rocketPitch);
			System.out.println("Target Yaw: " + targetYaw);
			System.out.println("Target Pitch: " + targetPitch);
		}
		
		turnSpeed = Math.min(maxTurn, turnSpeed / stepsRequired);
		
		/* ...and then we just cheat */
		if(stepsRequired <= 1) {
			turnSpeed = 180D;
		}
		
		/*if(stepsRequired > 1) {
			targetPitch = rocketPitch + ((targetPitch - rocketPitch) / stepsRequired);
		}*/

		if(debug) {
			System.out.println("=== ADJUSTED ===");
			System.out.println("Target Pitch: " + targetPitch);
		}

		/* shortest delta of α < 180° */
		double deltaYaw = ((targetYaw - rocketYaw) + 180D) % 360D - 180D;
		double deltaPitch = ((targetPitch - rocketPitch) + 180D) % 360D - 180D;

		double turnYaw = Math.min(Math.abs(deltaYaw), turnSpeed) * Math.signum(deltaYaw);
		double turnPitch = Math.min(Math.abs(deltaPitch), turnSpeed) * Math.signum(deltaPitch);

		if(debug) {
			System.out.println("=== RESULTS ===");
			System.out.println("Delta Yaw: " + deltaYaw);
			System.out.println("Delta Pitch: " + deltaPitch);
			System.out.println("Turn Yaw: " + turnYaw);
			System.out.println("Turn Pitch: " + turnPitch);
		}
		
		Vec3 velocity = Vec3.createVectorHelper(speed, 0, 0);
		velocity.rotateAroundZ((float) -Math.toRadians(rocketPitch + turnPitch));
		velocity.rotateAroundY((float) Math.toRadians(rocketYaw + turnYaw + 90));
		
		rocket.motionX = velocity.xCoord;
		rocket.motionY = velocity.yCoord;
		rocket.motionZ = velocity.zCoord;
	}
	
	private static double yaw(Vec3 vec) {
		boolean pos = vec.zCoord >= 0;
		return Math.toDegrees(Math.atan(vec.xCoord / vec.zCoord)) + (pos ? 180 : 0);
	}
	
	private static double pitch(Vec3 vec) {
		return Math.toDegrees(Math.atan(vec.yCoord / Math.sqrt(vec.xCoord * vec.xCoord + vec.zCoord * vec.zCoord)));
	}
}
