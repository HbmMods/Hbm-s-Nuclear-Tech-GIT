package com.hbm.dim.trait;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashBiMap;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public abstract class CelestialBodyTrait {

	// Similarly to fluid traits, we have classes, and instance members.
	// For the simple traits, we'll just init both here rather than two places.

	public static class CBT_Hot extends CelestialBodyTrait { }
	public static CBT_Hot HOT = new CBT_Hot();
	
	public static class CBT_Cold extends CelestialBodyTrait { }
	public static CBT_Cold COLD = new CBT_Cold();

	public static class CBT_War extends CelestialBodyTrait { }
	public static CBT_War WAR = new CBT_War();
	
	public static class CBT_SUNEXPLODED extends CelestialBodyTrait { }
	public static CBT_SUNEXPLODED SPLODE = new CBT_SUNEXPLODED();

	public static class CBT_Water extends CelestialBodyTrait { }
	public static CBT_Water HAS_WATER = new CBT_Water();

	// Constructor and loading
	public static List<Class<? extends CelestialBodyTrait>> traitList = new ArrayList<Class<? extends CelestialBodyTrait>>();
	public static HashBiMap<String, Class<? extends CelestialBodyTrait>> traitMap = HashBiMap.create();

	static {
		registerTrait("atmosphere", CBT_Atmosphere.class);
		registerTrait("hot", CBT_Hot.class);
		registerTrait("cold", CBT_Cold.class);
		registerTrait("hotter", CBT_Temperature.class);
		registerTrait("bees", CBT_Bees.class);
		registerTrait("war", CBT_War.class);
		registerTrait("sunexploded", CBT_SUNEXPLODED.class);
		registerTrait("water", CBT_Water.class);

	};

	private static void registerTrait(String name, Class<? extends CelestialBodyTrait> clazz) {
		traitList.add(clazz);
		traitMap.put(name, clazz);
	}
	
	// Serialization
	public void readFromNBT(NBTTagCompound nbt) { }
	public void writeToNBT(NBTTagCompound nbt) { }

	public void readFromBytes(ByteBuf buf) { }
	public void writeToBytes(ByteBuf buf) { }

}
