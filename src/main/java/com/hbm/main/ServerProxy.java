package com.hbm.main;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.sound.AudioWrapper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ServerProxy {
	
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
	
	public void playSound(String sound, Object data) { }
	
	public void displayTooltip(String msg) { }
	
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
}