package com.hbm.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * For receiving (sort of) complex control data via NBT from clients
 * @author hbm
 */
public interface IControlReceiver {

	public boolean hasPermission(EntityPlayer player);
	
	public void receiveControl(NBTTagCompound data);
}
