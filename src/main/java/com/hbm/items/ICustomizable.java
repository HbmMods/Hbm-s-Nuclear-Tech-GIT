package com.hbm.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ICustomizable {

	public void customize(EntityPlayer player, ItemStack stack, String... args);
}
