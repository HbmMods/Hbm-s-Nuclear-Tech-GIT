package com.hbm.tileentity;

import com.hbm.sound.AudioWrapper;

import api.hbm.tile.ILoadedTile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLoadedBase extends TileEntity implements ILoadedTile {
	
	public boolean isLoaded = true;
	public boolean muffled = false;
	
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.muffled = nbt.getBoolean("muffled");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("muffled", muffled);
	}
	
	public float getVolume(float baseVolume) {
		return muffled ? baseVolume * 0.1F : baseVolume;
	}
}
