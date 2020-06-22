package com.hbm.saveddata.satellites;

import net.minecraft.nbt.NBTTagCompound;

public class SatelliteMiner extends Satellite {
	
	public long lastOp;
	
	public SatelliteMiner() {
		this.satIface = Interfaces.NONE;
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("lastOp", lastOp);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		lastOp = nbt.getLong("lastOp");
	}
}
