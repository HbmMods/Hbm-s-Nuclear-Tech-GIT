package com.hbm.inventory.container;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.nbt.NBTTagCompound;

public interface ICustomPayloadReceiver {

	public void acceptData(Side side, int windowsId, NBTTagCompound data);
}
