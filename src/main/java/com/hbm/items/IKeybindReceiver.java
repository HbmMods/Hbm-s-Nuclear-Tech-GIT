package com.hbm.items;

import com.hbm.handler.HbmKeybinds.EnumKeybind;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IKeybindReceiver {

	public boolean canHandleKeybind(EntityPlayer player, ItemStack stack, EnumKeybind keybind);
	public void handleKeybind(EntityPlayer player, ItemStack stack, EnumKeybind keybind, boolean state);
}
