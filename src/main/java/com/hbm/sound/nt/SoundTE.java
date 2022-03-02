package com.hbm.sound.nt;

import net.minecraft.client.audio.ITickableSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class SoundTE implements ITickableSound {
	
	ISoundSourceTE source;
	
	private ResourceLocation sound;
	private boolean canRepeat = true;
	private int repeatDelay = 0;
	private boolean donePlaying = true;
	private float soundX;
	private float soundY;
	private float soundZ;
	private float volume;
	private float pitch;
	
	public SoundTE(String sound) {
		this.sound = new ResourceLocation(sound);
	}

	@Override
	public ResourceLocation getPositionedSoundLocation() {
		return this.sound;
	}

	@Override
	public boolean canRepeat() {
		return this.canRepeat;
	}

	@Override
	public int getRepeatDelay() {
		return this.repeatDelay;
	}

	@Override
	public float getVolume() {
		return this.volume;
	}

	@Override
	public float getPitch() {
		return this.pitch;
	}

	@Override
	public float getXPosF() {
		return this.soundX;
	}

	@Override
	public float getYPosF() {
		return this.soundY;
	}

	@Override
	public float getZPosF() {
		return this.soundZ;
	}

	@Override
	public AttenuationType getAttenuationType() {
		return AttenuationType.LINEAR;
	}

	@Override
	public void update() {
		
		if(this.source == null)
			return;
		
		this.volume = this.source.getVolume();
		this.pitch = this.source.getPitch();
		
		Vec3 pos = this.source.getSoundLocation();
		this.soundX = (float) pos.xCoord;
		this.soundY = (float) pos.yCoord;
		this.soundZ = (float) pos.zCoord;
		
	}

	@Override
	public boolean isDonePlaying() {
		return this.donePlaying;
	}
}
