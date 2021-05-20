package com.hbm.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockColored extends ItemBlock {

	public ItemBlockColored(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	public int getMetadata(int meta) {
		return meta;
	}
}
