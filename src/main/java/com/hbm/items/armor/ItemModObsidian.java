package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import com.hbm.util.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModObsidian extends ItemArmorMod {
	
	public ItemModObsidian() {
		super(ArmorModHandler.cladding, true, true, true, true);
	}
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKeyArray("armorMod.mod.Obsidian")[0]);
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.DARK_PURPLE + "  " + stack.getDisplayName() + I18nUtil.resolveKeyArray("armorMod.mod.Obsidian")[1]);
	}
}
