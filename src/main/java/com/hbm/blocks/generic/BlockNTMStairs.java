package com.hbm.blocks.generic;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockNTMStairs extends BlockStairs
{
	public BlockNTMStairs(Block baseBlock, int meta)
	{
		super(baseBlock, meta);
		setBlockTextureName(RefStrings.MODID + baseBlock.getUnlocalizedName().substring(5));
		setBlockName(baseBlock.getUnlocalizedName().substring(5).concat("_stairs"));
		setCreativeTab(MainRegistry.blockTab);
	}
	
}
