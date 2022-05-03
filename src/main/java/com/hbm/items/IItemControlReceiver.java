package com.hbm.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IItemControlReceiver {

	public void receiveControl(ItemStack stack, NBTTagCompound data);
}
