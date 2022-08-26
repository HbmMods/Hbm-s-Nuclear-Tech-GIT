package com.hbm.wiaj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.util.BobMathUtil;
import com.hbm.wiaj.actors.ISpecialActor;

import net.minecraft.util.MathHelper;

public class JarScript {

	public  WorldInAJar world;
	private List<JarScene> scenes = new ArrayList();
	public HashMap<Integer, ISpecialActor> actors = new HashMap();
	private JarScene currentScene;
	private int sceneNumber = 0;

	public double lastRotationYaw = -45D, rotationYaw = -45D;
	public double lastRotationPitch = -30D, rotationPitch = -30D;
	public double lastOffsetX = 0, offsetX = 0;
	public double lastOffsetY = 0, offsetY = 0;
	public double lastOffsetZ = 0, offsetZ = 0;
	
	public float interp = 0F;
	
	public long lastTick = 0;
	public int ticksElapsed = 0;
	
	private boolean isPaused = false;
	
	public JarScript(WorldInAJar world) {
		this.world = world;
	}
	
	public JarScript addScene(JarScene scene) {
		
		if(this.currentScene == null)
			this.currentScene = scene;
		
		this.scenes.add(scene);
		return this;
	}
	
	/**supposed to be called every frame, it calculates tick times and interp values */
	public void run() {
		long now = System.currentTimeMillis();
		
		boolean nextTick = false;
		
		if(this.lastTick == 0) { // do the first tick right away
			this.lastTick = now;
			nextTick = true;
		}
		
		if(this.lastTick + 50 < now) {
			this.lastTick = now;
			this.ticksElapsed++;
			nextTick = true;
		}
		
		if(this.currentScene != null) {
			this.interp = MathHelper.clamp_float((float) (now - this.lastTick) / 50F, 0F, 1F);
		} else {
			this.interp = 0;
		}
		
		if(nextTick) {
			
			this.lastRotationPitch = this.rotationPitch;
			this.lastRotationYaw = this.rotationYaw;
			this.lastOffsetX = this.offsetX;
			this.lastOffsetY = this.offsetY;
			this.lastOffsetZ = this.offsetZ;
			
			if(this.currentScene != null) {
				
				for(Entry<Integer, ISpecialActor> actor : this.actors.entrySet()) {
					actor.getValue().updateActor(this.currentScene);
				}
				
				tickScene();
			}
		}
	}
	
	public void tickScene() {
		this.currentScene.tick();
		
		if(this.currentScene.currentAction == null) { //means this scene is done
			
			this.sceneNumber++;
			
			if(this.sceneNumber < this.scenes.size()) {
				this.currentScene = this.scenes.get(sceneNumber);
				this.currentScene.currentActionStart = this.ticksElapsed;
			} else {
				this.currentScene = null;
			}
		}
	}
	
	private long pauseDelta;
	
	public void pause() {
		this.isPaused = true;
		this.pauseDelta = System.currentTimeMillis() - this.lastTick; //saves the difference between last tick and now
	}
	
	public void unpause() {
		this.isPaused = false;
		this.lastTick = System.currentTimeMillis() - this.pauseDelta; //recreates an equivalent last tick from the new current time
	}
	
	public boolean isPaused() {
		return this.isPaused;
	}
	
	public double yaw() { return BobMathUtil.interp(this.lastRotationYaw, this.rotationYaw, interp); }
	public double pitch() { return BobMathUtil.interp(this.lastRotationPitch, this.rotationPitch, interp); }
	public double offsetX() { return BobMathUtil.interp(this.lastOffsetX, this.offsetX, interp); }
	public double offsetY() { return BobMathUtil.interp(this.lastOffsetY, this.offsetY, interp); }
	public double offsetZ() { return BobMathUtil.interp(this.lastOffsetZ, this.offsetZ, interp); }
}
