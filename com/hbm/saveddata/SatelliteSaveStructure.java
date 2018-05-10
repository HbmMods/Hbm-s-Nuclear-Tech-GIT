package com.hbm.saveddata;

import java.util.Arrays;

import com.hbm.handler.FluidTypeHandler.FluidType;

import net.minecraft.nbt.NBTTagCompound;

public class SatelliteSaveStructure {

	public int satelliteID;
	public int satDim;
	public SatelliteType satelliteType;
	public long lastOp;
	
	public SatelliteSaveStructure() { }
	
	public SatelliteSaveStructure(int id, SatelliteType type, int dim) {
		satelliteID = id;
		satelliteType = type;
		satDim = dim;
	}
	
	public enum SatelliteType {
		
		//Prints map remotely
		MAPPER,
		//Displays entities
		RADAR,
		//Prints map, ores only
		SCANNER,
		//Does nothing
		RELAY,
		//Death ray
		LASER,
		//Allows use of AMS
		RESONATOR;
		
		public static SatelliteType getEnum(int i) {
			if(i < SatelliteType.values().length)
				return SatelliteType.values()[i];
			else
				return SatelliteType.RELAY;
		}
		
		public int getID() {
			return Arrays.asList(SatelliteType.values()).indexOf(this);
		}
	}

	public void readFromNBT(NBTTagCompound nbt, int index) {
		satelliteID = nbt.getInteger("sat_" + index + "_id");
		satelliteType = SatelliteType.getEnum(nbt.getInteger("sat_" + index + "_type"));
		satDim = nbt.getInteger("sat_" + index + "_dim");
		lastOp = nbt.getLong("sat_" + index + "_op");
	}

	public void writeToNBT(NBTTagCompound nbt, int index) {
		nbt.setInteger("sat_" + index + "_id", satelliteID);
		nbt.setInteger("sat_" + index + "_type", satelliteType.getID());
		nbt.setInteger("sat_" + index + "_dim", satDim);
		nbt.setLong("sat_" + index + "_op", lastOp);
	}

}
