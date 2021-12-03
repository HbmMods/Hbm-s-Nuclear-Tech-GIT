package com.hbm.items.block;

import com.hbm.blocks.generic.BlockConcreteColored;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

public class ItemBlockColoredConcrete extends ItemBlockBlastInfo {

	public ItemBlockColoredConcrete(Block block) {
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
