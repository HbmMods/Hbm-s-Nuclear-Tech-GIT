package com.hbm.main;

import com.hbm.sound.AudioWrapper;

public class ServerProxy
{
	public void registerRenderInfo()
	{
		
	}
	
	public void registerTileEntitySpecialRenderer() { }
	
	public void particleControl(double x, double y, double z, int type) { }

	public void spawnParticle(double x, double y, double z, String type, float[] args) { }

	public void registerMissileItems() { }

	public AudioWrapper getLoopedSound(String sound, float x, float y, float z, float volume, float pitch) { return null; }
}