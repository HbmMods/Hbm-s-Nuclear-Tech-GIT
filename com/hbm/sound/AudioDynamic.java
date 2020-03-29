package com.hbm.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class AudioDynamic extends MovingSound {

	protected AudioDynamic(ResourceLocation loc) {
		super(loc);
		this.repeat = true;
	}
	
	public void setPosition(float x, float y, float z) {
		this.xPosF = x;
		this.yPosF = y;
		this.zPosF = z;
	}

	@Override
	public void update() { }
	
	public void start() {
		Minecraft.getMinecraft().getSoundHandler().playSound(this);
	}
	
	public void stop() {
		Minecraft.getMinecraft().getSoundHandler().stopSound(this);
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public void setPitch(float pitch) {
		this.field_147663_c = pitch;
	}

}
