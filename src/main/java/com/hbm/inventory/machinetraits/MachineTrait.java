package com.hbm.inventory.machinetraits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashBiMap;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.machinetraits.MachineTraitSimple.*;

public class MachineTrait {
    	
	public static List<Class<? extends MachineTrait>> traitList = new ArrayList();
	public static HashBiMap<String, Class<? extends MachineTrait>> traitNameMap = HashBiMap.create();
	
	static {
		registerTrait("heatSource", MT_HeatSource.class);
		registerTrait("polluting", MT_Polluting.class);
		registerTrait("smoke", MT_Smoke.class);
		registerTrait("heavy", MT_Heavy.class);
		registerTrait("superheavy", MT_SuperHeavy.class);
		registerTrait("liquidstorage", MT_LiquidStorage.class);
		registerTrait("customhidden", MT_CustomHidden.class);
	}
	
	private static void registerTrait(String name, Class<? extends MachineTrait> clazz) {
		traitNameMap.put(name, clazz);
		traitList.add(clazz);
	}

	public void addInfo(List<String> info) { }
	public void addInfoHidden(List<String> info) { }
	
	public void serializeJSON(JsonWriter writer) throws IOException { }
	public void deserializeJSON(JsonObject obj) { }


}
