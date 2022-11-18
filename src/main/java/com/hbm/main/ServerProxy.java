package com.hbm.main;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.sound.AudioWrapper;
import com.hbm.sound.nt.ISoundSourceTE;
import com.hbm.sound.nt.SoundWrapper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class ServerProxy {

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
	public static final int ID_GAS_HAZARD = 9;
	
	public abstract void registerRenderInfo();
	public abstract void registerTileEntitySpecialRenderer();
	public abstract void registerItemRenderer();
	public abstract void registerEntityRenderer();
	public abstract void registerBlockRenderer();
	
	public abstract void particleControl(double x, double y, double z, int type);

	public abstract void spawnParticle(double x, double y, double z, String type, float... args);
	
	public abstract void effectNT(NBTTagCompound data);

	public abstract void registerMissileItems();

	public abstract AudioWrapper getLoopedSound(String sound, float x, float y, float z, float volume, float pitch);
	public abstract AudioWrapper getLoopedSoundStartStop(World world, String sound, String start, String stop, float x, float y, float z, float volume, float pitch);
	
	public abstract void playSound(String sound, Object data);

	public void displayTooltip(String msg, int id) {
		displayTooltip(msg, 1000, id);
	}
	public abstract void displayTooltip(String msg, int time, int id);
	
	public abstract boolean getIsKeyPressed(EnumKeybind key);
	public abstract EntityPlayer me();

	public abstract boolean isVanished(Entity e);

	public abstract void openLink(String url);
	
	@SuppressWarnings({ "unused", "static-method" })
	public SoundWrapper getTileSound(String sound, ISoundSourceTE source) {
		return new SoundWrapper();
	}
	
	@SuppressWarnings("static-method")
	public List<ItemStack> getSubItems(ItemStack stack) {
		
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(stack);
		return list;
	}
}