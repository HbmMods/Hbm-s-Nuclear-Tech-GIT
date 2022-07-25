package com.hbm.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockNamedMeta extends ItemBlockMeta {
	
	public ItemBlockNamedMeta(Block block) {
		super(block);
	}
	
	//OG class didn't have getUnlocalizedName and I didn't want to add it for fear of fucking shit up
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + stack.getItemDamage();
	}
	
}
