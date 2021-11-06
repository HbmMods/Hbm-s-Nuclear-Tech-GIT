package com.hbm.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockMeta extends ItemBlock {

	public ItemBlockMeta(Block p_i45326_1_) {
		super(p_i45326_1_);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	public int getMetadata(int p_77647_1_) {
		return p_77647_1_;
	}
}
