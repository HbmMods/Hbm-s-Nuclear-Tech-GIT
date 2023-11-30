package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModTwoKick extends ItemArmorMod {

	public ItemModTwoKick() {
		super(ArmorModHandler.servos, false, true, false, false);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.ITALIC + "\"I've had worse\"");
		list.add(EnumChatFormatting.YELLOW + "Punches fire 12 gauge shells");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.YELLOW + "  " + stack.getDisplayName() + " (Shotgun punches)");
	}
}
