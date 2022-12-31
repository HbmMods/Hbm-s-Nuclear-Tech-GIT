package com.hbm.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AudioWrapperClientStartStop extends AudioWrapperClient {

	public String start;
	public String stop;
	public World world;
	public float ssVol;
	public float x, y, z;
	
	public AudioWrapperClientStartStop(World world, ResourceLocation source, String start, String stop, float vol){
		super(source);
		if(sound != null){
			sound.setVolume(vol);
		}
		this.ssVol = vol;
		this.world = world;
		this.start = start;
		this.stop = stop;
	}
	
	@Override
	public void updatePosition(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
		super.updatePosition(x, y, z);
	}
	
	@Override
	public void startSound(){
		if(start != null){
			world.playSound(x, y, z, start, ssVol * 0.2F, 1, false);
		}
		super.startSound();
	}
	
	@Override
	public void stopSound(){
		if(stop != null){
			world.playSound(x, y, z, stop, ssVol * 0.2F, 1, false);
		}
		super.stopSound();
	}
	
	@Override
	public float getVolume(){
		return ssVol;
	}
}
