package com.hbm.wiaj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.wiaj.actors.ISpecialActor;

import net.minecraft.util.MathHelper;

public class JarScript {

	public  WorldInAJar world;
	private List<JarScene> scenes = new ArrayList();
	private HashMap<Integer, ISpecialActor> actors = new HashMap();
	private JarScene currentScene;
	private int sceneNumber = 0;

	public double lastRotationYaw = -45D;
	public double lastRotationPitch = -30D;
	public double rotationYaw = -45D;
	public double rotationPitch = -30D;
	
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
		
		this.interp = MathHelper.clamp_float((float) (now - this.lastTick) / 50F, 0F, 1F);
		
		if(nextTick) {
			this.lastRotationPitch = this.rotationPitch;
			this.lastRotationYaw = this.rotationYaw;
			
			if(this.currentScene != null) {
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
}
