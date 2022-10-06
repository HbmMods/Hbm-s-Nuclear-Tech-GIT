package com.hbm.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class AudioDynamic extends MovingSound {
	
	public float intendedVolume;

	protected AudioDynamic(ResourceLocation loc) {
		super(loc);
		this.repeat = true;
		this.field_147666_i = ISound.AttenuationType.LINEAR;
		this.intendedVolume = 10;
	}
	
	public void setPosition(float x, float y, float z) {
		this.xPosF = x;
		this.yPosF = y;
		this.zPosF = z;
	}

	@Override
	public void update() {

		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		float f = 0;
		
		if(player != null) {
			f = (float)Math.sqrt(Math.pow(xPosF - player.posX, 2) + Math.pow(yPosF - player.posY, 2) + Math.pow(zPosF - player.posZ, 2));
			volume = func(f, intendedVolume);
		} else {
			volume = intendedVolume;
		}
	}
	
	public void start() {
		Minecraft.getMinecraft().getSoundHandler().playSound(this);
	}
	
	public void stop() {
		Minecraft.getMinecraft().getSoundHandler().stopSound(this);
	}
	
	public void setVolume(float volume) {
		this.intendedVolume = volume;
	}
	
	public void setPitch(float pitch) {
		this.field_147663_c = pitch;
	}
	
	public float func(float f, float v) {
		return (f / v) * -2 + 2;
	}

	public boolean isPlaying() {
		return Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this);
	}
}
