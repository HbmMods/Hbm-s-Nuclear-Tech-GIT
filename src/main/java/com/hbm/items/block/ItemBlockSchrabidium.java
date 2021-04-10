package com.hbm.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockSchrabidium extends ItemBlockHazard {

	public ItemBlockSchrabidium(Block block) {
		super(block);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.rare;
	}
}
