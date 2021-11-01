package com.hbm.saveddata.satellites;

import net.minecraft.nbt.NBTTagCompound;

public class SatelliteLunarMiner extends Satellite {
	
	public long lastOp;
	
	public SatelliteLunarMiner() {
		this.satIface = Interfaces.NONE;
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("lastOp", lastOp);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		lastOp = nbt.getLong("lastOp");
	}
}
