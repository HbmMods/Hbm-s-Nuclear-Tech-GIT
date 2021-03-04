package com.hbm.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IControlReceiver {

	public boolean hasPermission(EntityPlayer player);
	
	public void receiveControl(NBTTagCompound data);
}
