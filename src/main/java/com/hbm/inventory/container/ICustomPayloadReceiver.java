package com.hbm.inventory.container;

import net.minecraft.nbt.NBTTagCompound;

public interface ICustomPayloadReceiver {

	public void acceptData(int windowsId, NBTTagCompound data);
}
