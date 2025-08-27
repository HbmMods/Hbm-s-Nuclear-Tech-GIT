package com.hbm.items.bomb;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFleija extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(I18nUtil.resolveKey("item.bomb_part.used_in"));
		list.add(ModBlocks.nuke_fleija.getLocalizedName());
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public EnumRarity getRarity(ItemStack itemstack) {
		if (this == ModItems.fleija_propellant) {
			return EnumRarity.rare;
		}
		return EnumRarity.common;
	}
}
