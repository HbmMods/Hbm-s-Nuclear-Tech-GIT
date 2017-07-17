package com.hbm.sound;

import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SoundLoopMachine extends PositionedSound implements ITickableSound {
	boolean donePlaying = false;
	TileEntity te;

	public SoundLoopMachine(ResourceLocation path, TileEntity te) {
		super(path);
		this.repeat = true;
		this.volume = 1;
		this.field_147663_c = 1;
		this.xPosF = te.xCoord;
		this.yPosF = te.yCoord;
		this.zPosF = te.zCoord;
		this.field_147665_h = 0;
		this.te = te;
	}

	@Override
	public void update() {
		if(te == null || (te != null && te.isInvalid()))
			donePlaying = true;
	}

	@Override
	public boolean isDonePlaying() {
		return this.donePlaying;
	}
	
	public void setVolume(float f) {
		volume = f;
	}
	
	public void setPitch(float f) {
		field_147663_c = f;
	}
	
	public void stop() {
		donePlaying = true;
	}
}
