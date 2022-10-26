package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockPinkSlab extends BlockSlab {

	public BlockPinkSlab(boolean bool, Material mat) {
		super(bool, mat);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return Item.getItemFromBlock(ModBlocks.pink_slab);
	}

	@Override
	protected ItemStack createStackedBlock(int i) {
		return new ItemStack(Item.getItemFromBlock(ModBlocks.pink_slab), 2, i & 7);
	}

	@Override
	public String func_150002_b(int p_150002_1_) {
		return null;
	}

}
