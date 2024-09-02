package com.hbm.items;

import com.hbm.handler.HbmKeybinds.EnumKeybind;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IKeybindReceiver {

	public void handleKeybind(EntityPlayer player, ItemStack stack, EnumKeybind keybind, boolean state);
}
