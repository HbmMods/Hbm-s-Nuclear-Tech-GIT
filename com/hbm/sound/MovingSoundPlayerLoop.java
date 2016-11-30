package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class MovingSoundPlayerLoop extends MovingSound {
<<<<<<< HEAD

	public static List<MovingSoundPlayerLoop> globalSoundList = new ArrayList<MovingSoundPlayerLoop>();
	public List<Entity> playerForSound = new ArrayList<Entity>();
=======
<<<<<<< HEAD

	public static List<MovingSoundPlayerLoop> globalSoundList = new ArrayList<MovingSoundPlayerLoop>();
	public List<Entity> playerForSound = new ArrayList<Entity>();
=======
	
	public static List<MovingSoundPlayerLoop> globalSoundList = new ArrayList<MovingSoundPlayerLoop>();
>>>>>>> 540fb3d256a0f4ae6a8b1db586f8e9cfd6ed7372
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af
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
<<<<<<< HEAD
		
=======
<<<<<<< HEAD
		
=======
>>>>>>> 540fb3d256a0f4ae6a8b1db586f8e9cfd6ed7372
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af
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
