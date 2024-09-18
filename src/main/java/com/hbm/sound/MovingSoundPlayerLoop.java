package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@Deprecated //wtf is this horseshit
public abstract class MovingSoundPlayerLoop extends MovingSound {

	public static List<MovingSoundPlayerLoop> globalSoundList = new ArrayList<MovingSoundPlayerLoop>();
	public List<Entity> playerForSound = new ArrayList<Entity>();
	public Entity player;
	public enum EnumHbmSound { soundTauLoop, soundChopperLoop, soundCrashingLoop, soundMineLoop };
	public EnumHbmSound type;
	public boolean init;

	public MovingSoundPlayerLoop(ResourceLocation res, Entity player, EnumHbmSound type) {
		super(res);
		this.player = player;
		this.type = type;
		this.init = false;
		this.repeat = true;
		if(MovingSoundPlayerLoop.getSoundByPlayer(player, type) == null)
			globalSoundList.add(this);
	}

	@Override
	public void update() {
		
		if(player != null) {
			this.xPosF = (float)player.posX;
			this.yPosF = (float)player.posY;
			this.zPosF = (float)player.posZ;
		}
		
		if(player == null || player.isDead)
			this.stop();
	}
	
	public void stop() {
		this.donePlaying = true;
		this.repeat = false;
		while(MovingSoundPlayerLoop.getSoundByPlayer(player, type) != null)
			globalSoundList.remove(MovingSoundPlayerLoop.getSoundByPlayer(player, type));
		
		this.player = null;
	}
	
	public static MovingSoundPlayerLoop getSoundByPlayer(Entity player, EnumHbmSound type) {
		
		for(MovingSoundPlayerLoop sound : globalSoundList) {
			if(sound.player == player && sound.type == type)
				return sound;
		}
		
		return null;
	}
	
	public void setPitch(float f) {
		this.field_147663_c = f;
	}
	
	public void setVolume(float f) {
		this.volume = f;
	}
	
	public void setDone(boolean b) {
		this.donePlaying = b;
	}

}
