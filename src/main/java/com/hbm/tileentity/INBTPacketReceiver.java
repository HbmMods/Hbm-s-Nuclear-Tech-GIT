package com.hbm.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTPacketReceiver {
	
	public void networkUnpack(NBTTagCompound nbt);
}
