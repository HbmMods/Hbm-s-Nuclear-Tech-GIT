package com.hbm.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

// A whole ass TE just for 2 extra bits
// My kingdom for 2 fucking flags

// Use this TE if you need more bits, that's it. Blame Mojang
public class TileEntityData extends TileEntity {

	public int metadata;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		metadata = nbt.getInteger("meta");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("meta", metadata);
	}

}
