package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModShackles extends ItemArmorMod {

	public ItemModShackles() {
		super(ArmorModHandler.extra, false, false, true, false);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.RED + "You will speak when I ask you to.");
		list.add(EnumChatFormatting.RED + "You will eat when I tell you to.");
		list.add(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "You will die when I allow you to.");
		
		list.add("");
		list.add(EnumChatFormatting.GOLD + "∞ revives left");
		list.add("");
		super.addInformation(stack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {

		list.add(EnumChatFormatting.GOLD + "  " + stack.getDisplayName() + " (∞ revives left)");
	}
}
