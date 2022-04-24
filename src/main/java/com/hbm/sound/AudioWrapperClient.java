package com.hbm.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class AudioWrapperClient extends AudioWrapper {
	
	AudioDynamic sound;
	
	public AudioWrapperClient(ResourceLocation source) {
		if(source != null)
			sound = new AudioDynamic(source);
	}
	
	public void updatePosition(float x, float y, float z) {
		if(sound != null)
			sound.setPosition(x, y, z);
	}
	
	public void updateVolume(float volume) {
		if(sound != null)
			sound.setVolume(volume);
	}
	
	public void updatePitch(float pitch) {
		if(sound != null)
			sound.setPitch(pitch);
	}
	
	public float getVolume() {
		if(sound != null)
			return sound.getVolume();
		return 1;
	}
	
	public float getPitch() {
		if(sound != null)
			return sound.getPitch();
		return 1;
	}
	
	public void startSound() {
		if(sound != null)
			sound.start();
	}
	
	public void stopSound() {
		if(sound != null)
			sound.stop();
	}

}
