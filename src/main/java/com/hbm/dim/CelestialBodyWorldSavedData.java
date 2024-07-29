package com.hbm.dim;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class CelestialBodyWorldSavedData extends WorldSavedData {

	private static final String DATA_NAME = "CelestialBodyData";

	public CelestialBodyWorldSavedData(String name) {
		super(name);
	}

	private long localTime;
	
	public static CelestialBodyWorldSavedData get(World world) {
		CelestialBodyWorldSavedData result = (CelestialBodyWorldSavedData) world.perWorldStorage.loadData(CelestialBodyWorldSavedData.class, DATA_NAME);
		
		if(result == null) {
			world.perWorldStorage.setData(DATA_NAME, new CelestialBodyWorldSavedData(DATA_NAME));
			result = (CelestialBodyWorldSavedData) world.perWorldStorage.loadData(CelestialBodyWorldSavedData.class, DATA_NAME);
		}
		
		return result;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		localTime = nbt.getLong("time");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("time", localTime);
	}

	public long getLocalTime() {
		return localTime;
	}

	public void setLocalTime(long time) {
		localTime = time;
		markDirty();
	}
	
}
