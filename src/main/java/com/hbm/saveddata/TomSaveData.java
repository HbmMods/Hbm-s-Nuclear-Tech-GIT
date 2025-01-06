package com.hbm.saveddata;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class TomSaveData extends WorldSavedData {

	public final static String key = "impactData";
	public float dust;
	public float fire;
	public boolean impact;
	
	private static TomSaveData lastCachedUnsafe = null;

	/* no caching for data per world needed, minecraft's save structure already does that! call forWorld as much as you want. */
	public static TomSaveData forWorld(World world) {
		TomSaveData result = (TomSaveData) world.perWorldStorage.loadData(TomSaveData.class, "impactData");

		if(result == null) {
			world.perWorldStorage.setData(key, new TomSaveData(key));
			result = (TomSaveData) world.perWorldStorage.loadData(TomSaveData.class, "impactData");
		}
		lastCachedUnsafe = result;
		return result;
	}
	
	/**
	 * Certain biome events do not have access to a world instance (very very bad), in those cases we have to rely on a possibly incorrect cached result.
	 * However, due to the world gen invoking TomSaveData.forWorld() quite a lot, it is safe to say that in most cases, we do end up with the correct result.
	 */
	public static TomSaveData getLastCachedOrNull() {
		return lastCachedUnsafe;
	}
	
	public static void resetLastCached() {
		lastCachedUnsafe = null;
	}

	public TomSaveData(String tagName) {
		super(tagName);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.dust = compound.getFloat("dust");
		this.fire = compound.getFloat("fire");
		this.impact = compound.getBoolean("impact");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat("dust", dust);
		nbt.setFloat("fire", fire);
		nbt.setBoolean("impact", impact);
	}
}