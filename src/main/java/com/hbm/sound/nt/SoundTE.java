package com.hbm.sound.nt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

/**
 * I have a dream. That one day every tile entity in this codebase will control their
 * own sounds. A codebase of the truly free, dammit. A codebase of functionality, not
 * patterns, ruled by necessity, not chance! Where the code changes to suit the use
 * case, not the other way around. Where methods and fields are back where they belong:
 * in the hands of the tile entities! Where every object is free to think - to calcilate
 * - for itself! Fuck all these limp-dick programmers and chickenshit coders. Fuck this
 * 24-hour Internet spew of design classes and Stack Overflow bullshit! Fuck duct tape
 * pride! Fuck the patterns! FUCK ALL OF IT! NTM is diseased. Rotten to the core.
 * There's no saving it - we need to pull it out by the classroot. Wipe the slate clean.
 * BURN IT DOWN! And from the ashes, the mother of all omelettes will be born. Evolved,
 * but unconventional! The broken will be purged and the cleanest will thrive - free to
 * function as they see fit, they'll make NTM great again! ...in my new NTM, tile
 * entities will expire and invalidate for what they BELIEVE! Not for memory space, not
 * for compatibility! Not for what they're told is professional. Every tile will be free
 * to load its own chunks! 
 * @author hbm
 *
 */
@SideOnly(Side.CLIENT)
public class SoundTE implements ISound {
	
	ISoundSourceTE source;
	
	private ResourceLocation sound;
	private boolean canRepeat = true;
	private int repeatDelay = 0;
	private float soundX;
	private float soundY;
	private float soundZ;
	private float volume;
	private float pitch;
	
	private boolean isSoundOn = false;
	
	public SoundTE(String sound, ISoundSourceTE source) {
		this.sound = new ResourceLocation(sound);
		this.source = source;
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

	/**
	 * Called by proxy of the SoundWrapper from the holding tile entity, once per tick.
	 */
	public void updateExternally() {
		
		SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();

		if(isSoundOn && !this.source.isPlaying()) {
			handler.stopSound(this);
			isSoundOn = false;
			return;
		}
		
		//TODO: drown this class, the minecraft sound handler and the entire fucking game in holy water
		//no, it won't fix anything but at least the pain will be over
		
		if(!isSoundOn && this.source.isPlaying()) {
			try {
				handler.playSound(this);
			} catch(IllegalArgumentException ex) { }
			isSoundOn = true;
		}
		
		this.volume = this.source.getVolume();
		this.pitch = this.source.getPitch();
		
		Vec3 pos = this.source.getSoundLocation();
		this.soundX = (float) pos.xCoord;
		this.soundY = (float) pos.yCoord;
		this.soundZ = (float) pos.zCoord;
		
	}
}
