package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class FT_Polluting extends FluidTrait {

	//original draft had both of them inside a hashmap for the release type but honestly handling hash maps in hash maps adds more complexity than it removes
	public HashMap<PollutionType, Float> releaseMap = new HashMap();
	public HashMap<PollutionType, Float> burnMap = new HashMap();

	public FT_Polluting release(PollutionType type, float amount) {
		releaseMap.put(type, amount);
		return this;
	}

	public FT_Polluting burn(PollutionType type, float amount) {
		burnMap.put(type, amount);
		return this;
	}

	@Override
	public void addInfo(List<String> info) {
		info.add(EnumChatFormatting.GOLD + "[Polluting]");
	}

	@Override
	public void addInfoHidden(List<String> info) {

		if(!this.releaseMap.isEmpty()) {
			info.add(EnumChatFormatting.GREEN + "When spilled:");
			for(Entry<PollutionType, Float> entry : releaseMap.entrySet()) info.add(EnumChatFormatting.GREEN + " - " + entry.getValue() + " " + entry.getKey() + " per mB");
		}

		if(!this.burnMap.isEmpty()) {
			info.add(EnumChatFormatting.RED + "When burned:");
			for(Entry<PollutionType, Float> entry : burnMap.entrySet()) info.add(EnumChatFormatting.RED + " - " + entry.getValue() + " " + entry.getKey() + " per mB");
		}
	}

	@Override
	public void onFluidRelease(World world, int x, int y, int z, FluidTank tank, int overflowAmount, FluidReleaseType type) {
		if(type == FluidReleaseType.SPILL) for(Entry<PollutionType, Float> entry : releaseMap.entrySet()) PollutionHandler.incrementPollution(world, x, y, z, entry.getKey(), entry.getValue());
		if(type == FluidReleaseType.BURN) for(Entry<PollutionType, Float> entry : burnMap.entrySet()) PollutionHandler.incrementPollution(world, x, y, z, entry.getKey(), entry.getValue());
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("release").beginObject();
		for(Entry<PollutionType, Float> entry : releaseMap.entrySet()) {
			writer.name(entry.getKey().name()).value(entry.getValue());
		}
		writer.endObject();
		writer.name("burn").beginObject();
		for(Entry<PollutionType, Float> entry : burnMap.entrySet()) {
			writer.name(entry.getKey().name()).value(entry.getValue());
		}
		writer.endObject();
	}

	@Override
	public void deserializeJSON(JsonObject obj) {
		if(obj.has("release")) {
			JsonObject release = obj.get("release").getAsJsonObject();
			for(PollutionType type : PollutionType.values()) {
				if(release.has(type.name())) {
					releaseMap.put(type, release.get(type.name()).getAsFloat());
				}
			}
		}
		if(obj.has("burn")) {
			JsonObject release = obj.get("burn").getAsJsonObject();
			for(PollutionType type : PollutionType.values()) {
				if(release.has(type.name())) {
					burnMap.put(type, release.get(type.name()).getAsFloat());
				}
			}
		}
	}

	public static void pollute(World world, int x, int y, int z, FluidType type, FluidReleaseType release, float mB) {
		FT_Polluting trait = type.getTrait(FT_Polluting.class);
		if(trait == null) return;
		if(release == FluidReleaseType.VOID) return;

		HashMap<PollutionType, Float> map = release == FluidReleaseType.BURN ? trait.burnMap : trait.releaseMap;

		for(Entry<PollutionType, Float> entry : map.entrySet()) {
			PollutionHandler.incrementPollution(world, x, y, z, entry.getKey(), entry.getValue() * mB);
		}
	}
}
