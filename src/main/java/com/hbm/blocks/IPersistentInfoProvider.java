package com.hbm.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public interface IPersistentInfoProvider {

	public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext);
}
