package com.hbm.blocks;

import net.minecraft.block.Block;

public class ModSoundType extends Block.SoundType {

	public ModSoundType(String name, float volume, float pitch) {
		super(name, volume, pitch);
	}

	public String getBreakSound() {
		return "hbm:" + super.getBreakSound();
	}

	public String getStepResourcePath() {
		return super.getStepResourcePath();
	}

}
