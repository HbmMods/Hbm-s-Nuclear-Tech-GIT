package com.hbm.sound.nt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SoundWrapperClient extends SoundWrapper {

	private SoundTE sound;
	
	public SoundWrapperClient(String sound, ISoundSourceTE source) {
		this.sound = new SoundTE(sound, source);
	}
	
	@Override
	public void updateSound() {
		this.sound.updateExternally();
	}
}
