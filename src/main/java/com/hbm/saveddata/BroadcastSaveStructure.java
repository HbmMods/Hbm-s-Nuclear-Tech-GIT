package com.hbm.saveddata;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;

public class BroadcastSaveStructure {

	public int broadcastID;
	public BroadcastType broadcastType;
	public int posX;
	public int posZ;
	
	public BroadcastSaveStructure() { }
	
	public BroadcastSaveStructure(int id, BroadcastType type) {
		broadcastID = id;
		broadcastType = type;
	}
	
	public enum BroadcastType {
		
		DEMO;
		
		public static BroadcastType getEnum(int i) {
			if(i < BroadcastType.values().length)
				return BroadcastType.values()[i];
			else
				return BroadcastType.DEMO;
		}
		
		public int getID() {
			return Arrays.asList(BroadcastType.values()).indexOf(this);
		}
	}

	public void readFromNBT(NBTTagCompound nbt, int index) {
		broadcastID = nbt.getInteger("bc_" + index + "_id");
		broadcastType = BroadcastType.getEnum(nbt.getInteger("bc_" + index + "_type"));
		posX = nbt.getInteger("bc_" + index + "_x");
		posZ = nbt.getInteger("bc_" + index + "_z");
	}

	public void writeToNBT(NBTTagCompound nbt, int index) {
		nbt.setInteger("bc_" + index + "_id", broadcastID);
		nbt.setInteger("bc_" + index + "_type", broadcastType.getID());
		nbt.setInteger("bc_" + index + "_x", posX);
		nbt.setInteger("bc_" + index + "_z", posZ);
	}

}
