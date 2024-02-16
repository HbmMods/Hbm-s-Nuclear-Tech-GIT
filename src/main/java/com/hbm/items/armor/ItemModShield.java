package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModShield extends ItemArmorMod {
	
	public final float shield;

	public ItemModShield(float shield) {
		super(ArmorModHandler.kevlar, false, true, false, false);
		this.shield = shield;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		String color = "" + (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.YELLOW : EnumChatFormatting.GOLD);
		list.add(color + "+" + (Math.round(shield * 10) * 0.1) + " shield");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		String color = "" + (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.YELLOW : EnumChatFormatting.GOLD);
		list.add(color + "  " + stack.getDisplayName() + " (+" + (Math.round(shield * 10) * 0.1) + " health)");
	}
}
