package com.hbm.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class AudioWrapperClient extends AudioWrapper {
	
	AudioDynamic sound;
	
	public AudioWrapperClient(ResourceLocation source) {
		sound = new AudioDynamic(source);
	}
	
	public void updatePosition(float x, float y, float z) {
		sound.setPosition(x, y, z);
	}
	
	public void updateVolume(float volume) {
		sound.setVolume(volume);
	}
	
	public void updatePitch(float pitch) {
		sound.setPitch(pitch);
	}
	
	public void startSound() {
		sound.start();
	}
	
	public void stopSound() {
		sound.stop();
	}

}
