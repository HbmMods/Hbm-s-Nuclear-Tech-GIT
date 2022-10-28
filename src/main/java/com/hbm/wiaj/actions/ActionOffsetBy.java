package com.hbm.wiaj.actions;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;

/**
 * Static action for moving the scene around
 * To move the scene along with another action, create a special actor that moves the scene
 * @author hbm
 */
public class ActionOffsetBy implements IJarAction {
	
	int time;
	double motionX;
	double motionY;
	double motionZ;
	
	public ActionOffsetBy(double x, double y, double z, int time) {
		this.motionX = x / (time + 1);
		this.motionY = y / (time + 1);
		this.motionZ = z / (time + 1);
		this.time = time;
	}

	@Override
	public int getDuration() {
		return this.time;
	}

	@Override
	public void act(WorldInAJar world, JarScene scene) {
		scene.script.offsetX += this.motionX;
		scene.script.offsetY += this.motionY;
		scene.script.offsetZ += this.motionZ;
		
		if(this.time == 0) {
			scene.script.lastOffsetX = scene.script.offsetX;
			scene.script.lastOffsetY = scene.script.offsetY;
			scene.script.lastOffsetZ = scene.script.offsetZ;
		}
	}
}
