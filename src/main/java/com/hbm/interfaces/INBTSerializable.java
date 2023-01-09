package com.hbm.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerializable
{
	public void writeToNBT(NBTTagCompound nbt);
	public void readFromNBT(NBTTagCompound nbt);
	
	public default NBTTagCompound writeToNBT()
	{
		final NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return nbt;
	}
}
