package com.hbm.blocks.test;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class TestBB extends Block {

	public TestBB(Material mat) {
		super(mat);
		
		if(this == ModBlocks.test_bb_bork)
			this.setBlockBounds(-1000F, -1000F, -1000F, 1001F, 1001F, 1001F);
		else
			this.setBlockBounds(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
	}
}
