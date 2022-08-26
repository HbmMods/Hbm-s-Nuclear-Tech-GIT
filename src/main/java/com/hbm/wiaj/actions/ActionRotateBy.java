package com.hbm.wiaj.actions;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;

/**
 * Static action for rotating the scene around
 * To rotate the scene along with another action, create a special actor that rotates the scene
 * @author hbm
 */
public class ActionRotateBy implements IJarAction {
	
	int time;
	double velYaw;
	double velPitch;

	public ActionRotateBy(double yaw, double pitch, int time) {
		this.velYaw = yaw / (time + 1);
		this.velPitch = pitch / (time + 1);
		this.time = time;
	}

	@Override
	public int getDuration() {
		return this.time;
	}

	@Override
	public void act(WorldInAJar world, JarScene scene) {
		scene.script.rotationPitch += this.velPitch;
		scene.script.rotationYaw += this.velYaw;
	}
}
