package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockCluster extends Block {

	public BlockCluster(Material mat) {
		super(mat);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return getDrop();
	}
	
	private Item getDrop() {

		if(this == ModBlocks.cluster_iron)		return ModItems.crystal_iron;
		if(this == ModBlocks.cluster_titanium)	return ModItems.crystal_titanium;
		if(this == ModBlocks.cluster_aluminium)	return ModItems.crystal_aluminium;
		if(this == ModBlocks.cluster_copper)	return ModItems.crystal_copper;
		
		return null;
	}
}
