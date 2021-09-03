package com.hbm.items.weapon.gununified;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IReloadBehavior {

	public boolean tryStartReload(ItemStack stack, EntityPlayer player);
	public boolean updateRelaod(ItemStack stack, EntityPlayer player);
}
