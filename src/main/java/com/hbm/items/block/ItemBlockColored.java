package com.hbm.items.block;

import com.hbm.blocks.generic.BlockConcreteColored;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

public class ItemBlockColored extends ItemBlockBlastInfo {

	public ItemBlockColored(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	public int getMetadata(int meta) {
		return meta;
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + ItemDye.field_150923_a[BlockConcreteColored.func_150032_b(stack.getItemDamage())];
	}
}
