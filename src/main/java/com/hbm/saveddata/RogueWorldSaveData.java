package com.hbm.saveddata;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class RogueWorldSaveData extends WorldSavedData {

	public final static String key = "rogueWorldData";
	public float distance;
	public float temperature;
	public boolean rogue;
	public boolean star;
	public long rtime;
	public float atmosphere;
	
	private static RogueWorldSaveData lastCachedUnsafe = null;

	/* no caching for data per world needed, minecraft's save structure already does that! call forWorld as much as you want. */
	public static RogueWorldSaveData forWorld(World world) {
		RogueWorldSaveData result = (RogueWorldSaveData) world.perWorldStorage.loadData(RogueWorldSaveData.class, "rogueWorldData");

		if(result == null) {
			world.perWorldStorage.setData(key, new RogueWorldSaveData(key));
			result = (RogueWorldSaveData) world.perWorldStorage.loadData(RogueWorldSaveData.class, "rogueWorldData");
		}
		lastCachedUnsafe = result;
		return result;
	}
	
	/**
	 * Certain biome events do not have access to a world instance (very very bad), in those cases we have to rely on a possibly incorrect cached result.
	 * However, due to the world gen invoking TomSaveData.forWorld() quite a lot, it is safe to say that in most cases, we do end up with the correct result.
	 */
	public static RogueWorldSaveData getLastCachedOrNull() {
		return lastCachedUnsafe;
	}

	public RogueWorldSaveData(String tagName) {
		super(tagName);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.distance = compound.getFloat("distance");
		this.temperature = compound.getFloat("temperature");
		this.atmosphere = compound.getFloat("atmosphere");
		this.rogue = compound.getBoolean("rogue");
		this.star = compound.getBoolean("star");
		this.rtime = compound.getLong("rtime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat("distance", distance);
		nbt.setFloat("temperature", temperature);
		nbt.setFloat("atmosphere", atmosphere);
		nbt.setBoolean("star", star);
		nbt.setBoolean("rogue", rogue);
		nbt.setLong("rtime", rtime);
	}
}