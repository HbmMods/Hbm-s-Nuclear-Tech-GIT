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
	/* this was the easiest way of doing this without needing to change all 7 quadrillion implementors */
	public default void receiveControl(EntityPlayer player, NBTTagCompound data) { }
}
