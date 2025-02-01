package com.hbm.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IEquipReceiver {
	
	public void onEquip(EntityPlayer player, ItemStack stack);

}
