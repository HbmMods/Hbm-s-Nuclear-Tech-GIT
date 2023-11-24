package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import com.hbm.util.I18nUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModTwoKick extends ItemArmorMod {

	public ItemModTwoKick() {
		super(ArmorModHandler.servos, false, true, false, false);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKeyArray("armorMod.mod.TwoKick")[0]);
		list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKeyArray("armorMod.mod.TwoKick")[1]);
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.YELLOW + "  " + stack.getDisplayName() + I18nUtil.resolveKeyArray("armorMod.mod.TwoKick")[2]);
	}
}
