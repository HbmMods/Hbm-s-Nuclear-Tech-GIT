package com.hbm.dim.trait;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashBiMap;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public abstract class CelestialBodyTrait {

	// Similarly to fluid traits, we have classes, and instance members.
	// For the simple traits, we'll just init both here rather than two places.

	public static class CBT_War extends CelestialBodyTrait { }
	public static CBT_War WAR = new CBT_War();
	
	public static class CBT_Destroyed extends CelestialBodyTrait { }
	public static CBT_Destroyed SPLODE = new CBT_Destroyed();

	public static class CBT_Water extends CelestialBodyTrait { }
	public static CBT_Water HAS_WATER = new CBT_Water();

	// Constructor and loading
	public static List<Class<? extends CelestialBodyTrait>> traitList = new ArrayList<Class<? extends CelestialBodyTrait>>();
	public static HashBiMap<String, Class<? extends CelestialBodyTrait>> traitMap = HashBiMap.create();

	static {
		registerTrait("atmosphere", CBT_Atmosphere.class);
		registerTrait("temperature", CBT_Temperature.class);
		registerTrait("bees", CBT_Bees.class);
		registerTrait("war", CBT_War.class);
		registerTrait("destroyed", CBT_Destroyed.class);
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
