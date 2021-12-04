package com.hbm.blocks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public interface ITooltipProvider {

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext);
	
	public default EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.common;
	}
}
