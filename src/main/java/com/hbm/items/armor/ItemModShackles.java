package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import com.hbm.util.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModShackles extends ItemArmorMod {

	public ItemModShackles() {
		super(ArmorModHandler.extra, false, false, true, false);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("armorMod.mod.Shackles")[0]);
		list.add(EnumChatFormatting.RED + I18nUtil.resolveKeyArray("armorMod.mod.Shackles")[1]);
		list.add(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + I18nUtil.resolveKeyArray("armorMod.mod.Shackles")[2]);
		
		list.add("");
		list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKeyArray("armorMod.mod.Shackles")[3]);
		list.add("");
		super.addInformation(stack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {

		list.add(EnumChatFormatting.GOLD + "  " + stack.getDisplayName() + I18nUtil.resolveKeyArray("armorMod.mod.Shackles")[4]);
	}
}
