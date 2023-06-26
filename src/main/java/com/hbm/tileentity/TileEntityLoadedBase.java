package com.hbm.tileentity;

import com.hbm.sound.AudioWrapper;

import api.hbm.energy.ILoadedTile;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLoadedBase extends TileEntity implements ILoadedTile {
	
	public boolean isLoaded = true;
	
	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		this.isLoaded = false;
	}
	
	public AudioWrapper createAudioLoop() { return null; }
	
	public AudioWrapper rebootAudio(AudioWrapper wrapper) {
		wrapper.stopSound();
		AudioWrapper audio = createAudioLoop();
		audio.startSound();
		return audio;
	}
}
