package com.hbm.sound.nt;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public interface ISoundSourceTE {
	
	public boolean isPlaying();

	public default Vec3 getSoundLocation() {
		TileEntity te = (TileEntity) this;
		return Vec3.createVectorHelper(te.xCoord + 0.5, te.yCoord + 0.5, te.zCoord + 0.5);
	}
	
	public default float getVolume() {
		return 1F;
	}
	
	public default float getPitch() {
		return 1F;
	}
}
