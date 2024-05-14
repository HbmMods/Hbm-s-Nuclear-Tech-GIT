package com.hbm.dim.trait;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashBiMap;

import net.minecraft.nbt.NBTTagCompound;

public abstract class CelestialBodyTrait {

	// Similarly to fluid traits, we have classes, and instance members.
	// For the simple traits, we'll just init both here rather than two places.
	
	// Breathable MAY be just replaced with a check against PT_Atmosphere
	public static class CBT_Breathable extends CelestialBodyTrait { }
	public static CBT_Breathable BREATHABLE = new CBT_Breathable();

	public static class CBT_Hot extends CelestialBodyTrait { }
	public static CBT_Hot HOT = new CBT_Hot();
	
	public static class CBT_Cold extends CelestialBodyTrait { }
	public static CBT_Cold COLD = new CBT_Cold();


	// Constructor and loading
	public static List<Class<? extends CelestialBodyTrait>> traitList = new ArrayList<Class<? extends CelestialBodyTrait>>();
	public static HashBiMap<String, Class<? extends CelestialBodyTrait>> traitMap = HashBiMap.create();

	static {
		registerTrait("atmosphere", CBT_Atmosphere.class);
		registerTrait("breathable", CBT_Breathable.class);
		registerTrait("hot", CBT_Hot.class);
		registerTrait("cold", CBT_Cold.class);
		registerTrait("hotter", CBT_Temperature.class);

	};

	private static void registerTrait(String name, Class<? extends CelestialBodyTrait> clazz) {
		traitList.add(clazz);
		traitMap.put(name, clazz);
	}

	// Serialization
	public void readFromNBT(NBTTagCompound nbt) {

	}

	public void writeToNBT(NBTTagCompound nbt) {
		String traitName = traitMap.inverse().get(this.getClass());
		nbt.setString("name", traitName);
	}

	
}
