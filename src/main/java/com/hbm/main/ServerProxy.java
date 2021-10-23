package com.hbm.main;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.sound.AudioWrapper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ServerProxy {
	
	public abstract void registerRenderInfo();
	public abstract void registerTileEntitySpecialRenderer();
	public abstract void registerItemRenderer();
	public abstract void registerEntityRenderer();
	public abstract void registerBlockRenderer();
	
	public abstract void particleControl(double x, double y, double z, int type);

	public abstract void spawnParticle(double x, double y, double z, String type, float[] args);
	
	public abstract void effectNT(NBTTagCompound data);

	public abstract void registerMissileItems();

	public abstract AudioWrapper getLoopedSound(String sound, float x, float y, float z, float volume, float pitch);
	
	public abstract void playSound(String sound, Object data);
	
	public abstract boolean isVanished(Entity e);
	public abstract void displayTooltip(String msg);
	
	public abstract boolean getIsKeyPressed(EnumKeybind key);
	public abstract EntityPlayer me();
}