package com.hbm.main;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.sound.AudioWrapper;
import com.hbm.sound.nt.ISoundSourceTE;
import com.hbm.sound.nt.SoundWrapper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ServerProxy {

	//sort by estimated time of display. longer lasting ones should be sorted at the top.
	public static final int ID_DUCK = 0;
	public static final int ID_FILTER = 1;
	public static final int ID_COMPASS = 2;
	public static final int ID_CABLE = 3;
	public static final int ID_JETPACK = 4;
	public static final int ID_HUD = 5;
	public static final int ID_DETONATOR = 6;
	public static final int ID_FLUID_ID = 7;
	public static final int ID_GUN_MODE = 8;
	
	public void registerRenderInfo() { }
	public void registerTileEntitySpecialRenderer() { }
	public void registerItemRenderer() { }
	public void registerEntityRenderer() { }
	public void registerBlockRenderer() { }
	
	public void particleControl(double x, double y, double z, int type) { }

	public void spawnParticle(double x, double y, double z, String type, float[] args) { }
	
	public void effectNT(NBTTagCompound data) { }

	public void registerMissileItems() { }

	public AudioWrapper getLoopedSound(String sound, float x, float y, float z, float volume, float pitch) { return null; }
	public AudioWrapper getLoopedSoundStartStop(World world, String sound, String start, String stop, float x, float y, float z, float volume, float pitch) { return null; }
	
	public void playSound(String sound, Object data) { }

	public void displayTooltip(String msg, int id) {
		displayTooltip(msg, 1000, id);
	}
	public void displayTooltip(String msg, int time, int id) { }
	
	public boolean getIsKeyPressed(EnumKeybind key) {
		return false;
	}
	public EntityPlayer me() {
		return null;
	}

	public boolean isVanished(Entity e) {
		return false;
	}
	
	public void openLink(String url) { }
	
	public SoundWrapper getTileSound(String sound, ISoundSourceTE source) {
		return new SoundWrapper();
	}
}