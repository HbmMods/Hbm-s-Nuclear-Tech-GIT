package com.hbm.sound;

public class AudioWrapper {
	
	public void updatePosition(float x, float y, float z) { }
	
	public void updateVolume(float volume) { }
	
	public void updatePitch(float pitch) { }
	
	public float getVolume() { return 0F; }
	
	public float getPitch() { return 0F; }
	
	public void setDoesRepeat(boolean repeats) { }
	
	public void startSound() { }
	
	public void stopSound() { }
	
	public boolean isPlaying() { return false; }
}
