package com.hbm.main;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.saveddata.TomSaveData;
import com.hbm.sound.AudioWrapper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
	public static final int ID_TOOLABILITY = 8;
	public static final int ID_GUN_MODE = 9;
	public static final int ID_GAS_HAZARD = 10;
	
	public void registerRenderInfo() { }
	public void registerTileEntitySpecialRenderer() { }
	public void registerItemRenderer() { }
	public void registerEntityRenderer() { }
	public void registerBlockRenderer() { }
	
	public void particleControl(double x, double y, double z, int type) { }

	public void spawnParticle(double x, double y, double z, String type, float[] args) { }
	
	public void effectNT(NBTTagCompound data) { }

	public void registerMissileItems() { }

	public AudioWrapper getLoopedSound(String sound, float x, float y, float z, float volume, float range, float pitch) { return null; }
	public AudioWrapper getLoopedSound(String sound, float x, float y, float z, float volume, float range, float pitch, int keepAlive) { return null; }
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
	
	public List<ItemStack> getSubItems(ItemStack stack) {
		
		List<ItemStack> list = new ArrayList();
		list.add(stack);
		return list;
	}
	
	public float getImpactDust(World world) {
		return TomSaveData.forWorld(world).dust;
	}
	
	public float getImpactFire(World world) {
		return TomSaveData.forWorld(world).fire;
	}
	
	public boolean getImpact(World world) {
		return TomSaveData.forWorld(world).impact;
	}
}