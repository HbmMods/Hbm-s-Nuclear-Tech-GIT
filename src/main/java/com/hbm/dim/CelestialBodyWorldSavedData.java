package com.hbm.dim;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class CelestialBodyWorldSavedData extends WorldSavedData {

	private static final String DATA_NAME = "CelestialBodyData";

	public CelestialBodyWorldSavedData(String name) {
		super(name);
	}

	private WorldProviderCelestial provider;
	private long localTime;
	
	public static CelestialBodyWorldSavedData get(WorldProviderCelestial provider) {
		CelestialBodyWorldSavedData result = (CelestialBodyWorldSavedData) provider.worldObj.perWorldStorage.loadData(CelestialBodyWorldSavedData.class, DATA_NAME);
		
		if(result == null) {
			provider.worldObj.perWorldStorage.setData(DATA_NAME, new CelestialBodyWorldSavedData(DATA_NAME));
			result = (CelestialBodyWorldSavedData) provider.worldObj.perWorldStorage.loadData(CelestialBodyWorldSavedData.class, DATA_NAME);
		}

		result.provider = provider;
		
		return result;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		localTime = nbt.getLong("time");
		provider.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("time", localTime);
		provider.writeToNBT(nbt);
	}

	public long getLocalTime() {
		return localTime;
	}

	public void setLocalTime(long time) {
		localTime = time;
		markDirty();
	}
	
}
