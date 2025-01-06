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

	public float maxVolume = 1;
	public float range;
	public int keepAlive;
	public int timeSinceKA;;
	public boolean shouldExpire = false;;

	protected AudioDynamic(ResourceLocation loc) {
		super(loc);
		this.repeat = true;
		this.field_147666_i = ISound.AttenuationType.NONE;
		this.range = 10;
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
			volume = func(f);
		} else {
			volume = maxVolume;
		}
		
		if(this.shouldExpire) {
			
			if(this.timeSinceKA > this.keepAlive) {
				this.stop();
			}
			
			this.timeSinceKA++;
		}
	}
	
	public void start() {
		Minecraft.getMinecraft().getSoundHandler().playSound(this);
	}
	
	public void stop() {
		Minecraft.getMinecraft().getSoundHandler().stopSound(this);
	}
	
	public void setVolume(float volume) {
		this.maxVolume = volume;
	}
	
	public void setRange(float range) {
		this.range = range;
	}
	
	public void setKeepAlive(int keepAlive) {
		this.keepAlive = keepAlive;
		this.shouldExpire = true;
	}
	
	public void keepAlive() {
		this.timeSinceKA = 0;
	}
	
	public void setPitch(float pitch) {
		this.field_147663_c = pitch;
	}
	
	public float func(float dist) {
		return (dist / range) * -maxVolume + maxVolume;
	}

	public boolean isPlaying() {
		return Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this);
	}
}
