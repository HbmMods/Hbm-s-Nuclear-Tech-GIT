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
	public List<JarScene> scenes = new ArrayList();
	public HashMap<Integer, ISpecialActor> actors = new HashMap();
	public JarScene currentScene;
	public int sceneNumber = 0;

	public double lastRotationYaw = -45D, rotationYaw = -45D;
	public double lastRotationPitch = -30D, rotationPitch = -30D;
	public double lastOffsetX = 0, offsetX = 0;
	public double lastOffsetY = 0, offsetY = 0;
	public double lastOffsetZ = 0, offsetZ = 0;
	public double lastZoom = 1, zoom = 1;
	
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
		
		if(this.isPaused && !freeRun) return;
		
		long now = System.currentTimeMillis();
		
		boolean nextTick = false;
		
		if(this.lastTick == 0) { // do the first tick right away
			this.lastTick = now;
			nextTick = true;
		}
		
		if(this.lastTick + 50 < now || freeRun) {
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
			this.lastZoom = this.zoom;
			
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
				this.currentScene.reset();
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
	
	private void ffw() {
		
		this.reset();
		
		freeRun = true;
		int i = 0;
		
		while(this.sceneNumber < ffwTarget && this.currentScene != null && i < 10_000) {
			this.run();
			i++;
		}
		
		if(i > 0) { //i don't know why it needs one more cycle but it does
			this.run();
		}
		
		freeRun = false;
	}
	
	/** how far we want to fast forward */
	public static int ffwTarget = 0;
	/** flag set during FFW, skips tick delay checks which means ticks during run() are always executed */
	public static boolean freeRun = false; 
	
	public void reset() {
		
		this.actors.clear();
		this.world.nuke();
		
		this.currentScene = this.scenes.get(0);
		this.sceneNumber = 0;
		this.ticksElapsed = 0;
		this.lastTick = 0;

		this.lastOffsetX = this.offsetX = 0D;
		this.lastOffsetY = this.offsetY = 0D;
		this.lastOffsetZ = this.offsetZ = 0D;
		this.lastZoom = this.zoom = 1D;
		this.lastRotationYaw = this.rotationYaw = -45D;
		this.lastRotationPitch = this.rotationPitch = -30D;
		
		for(JarScene scene : this.scenes) {
			scene.reset();
		}
	}
	
	public void rewindOne() {
		
		if(this.sceneNumber > 0) {
			this.ffwTarget = this.sceneNumber - 1;
		} else {
			this.ffwTarget = 0;
		}
		
		ffw();
	}
	
	public void forwardOne() {
		if(this.sceneNumber < this.scenes.size()) {
			this.ffwTarget = this.sceneNumber + 1;
		} else {
			this.ffwTarget = this.scenes.size();
		}
		
		ffw();
	}
	
	public double yaw() { return BobMathUtil.interp(this.lastRotationYaw, this.rotationYaw, interp); }
	public double pitch() { return BobMathUtil.interp(this.lastRotationPitch, this.rotationPitch, interp); }
	public double offsetX() { return BobMathUtil.interp(this.lastOffsetX, this.offsetX, interp); }
	public double offsetY() { return BobMathUtil.interp(this.lastOffsetY, this.offsetY, interp); }
	public double offsetZ() { return BobMathUtil.interp(this.lastOffsetZ, this.offsetZ, interp); }
	public double zoom() { return BobMathUtil.interp(this.lastZoom, this.zoom, interp); }
}
