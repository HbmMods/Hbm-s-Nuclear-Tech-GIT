package com.hbm.sound.nt;

import net.minecraft.util.Vec3;

public interface ISoundSourceTE {

	public Vec3 getSoundLocation();
	public boolean isPlaying();
	
	public default float getVolume() {
		return 1F;
	}
	
	public default float getPitch() {
		return 1F;
	}
}
