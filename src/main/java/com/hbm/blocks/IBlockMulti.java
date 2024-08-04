package com.hbm.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IBlockMulti {

	public int getSubCount();
	
	public default String getUnlocalizedName(ItemStack stack) {
		return ((Block)this).getUnlocalizedName();
	}
	
	public default String getOverrideDisplayName(ItemStack stack) {
		return null;
	}
	
	public default int rectify(int meta) {
		return Math.abs(meta % getSubCount());
	}
}
