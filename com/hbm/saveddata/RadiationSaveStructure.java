package com.hbm.saveddata;

import java.util.Arrays;

import com.hbm.handler.FluidTypeHandler.FluidType;

import net.minecraft.nbt.NBTTagCompound;

public class RadiationSaveStructure {

	public int chunkX;
	public int chunkY;
	public float radiation;
	
	public RadiationSaveStructure() { }
	
	public RadiationSaveStructure(int x, int y, float rad) {
		chunkX = x;
		chunkY = y;
		radiation = rad;
	}

	public void readFromNBT(NBTTagCompound nbt, int index) {
		chunkX = nbt.getInteger("rad_" + index + "_x");
		chunkY = nbt.getInteger("rad_" + index + "_y");
		radiation = nbt.getFloat("rad_" + index + "_level");
	}

	public void writeToNBT(NBTTagCompound nbt, int index) {
		nbt.setInteger("rad_" + index + "_x", chunkX);
		nbt.setInteger("rad_" + index + "_y", chunkY);
		nbt.setFloat("rad_" + index + "_level", radiation);
	}

}
