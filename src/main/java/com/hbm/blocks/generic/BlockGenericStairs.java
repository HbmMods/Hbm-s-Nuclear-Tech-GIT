package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockGenericStairs extends BlockStairs {

	public static List<Object[]> recipeGen = new ArrayList();

	public BlockGenericStairs(Block block, int meta) {
		super(block, meta);
		this.useNeighborBrightness = true;
		
		recipeGen.add(new Object[] {block, meta, this});
		
		this.setBlockTextureName(RefStrings.MODID + ":concrete");
	}
}
