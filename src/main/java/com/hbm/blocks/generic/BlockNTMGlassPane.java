package com.hbm.blocks.generic;

import com.hbm.interfaces.Untested;
import com.hbm.lib.RefStrings;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockNTMGlassPane extends BlockPane
{
	@Untested
	public BlockNTMGlassPane(String itemTex, String blockTex, Material mat, boolean dropWhenBroken)
	{
		super(RefStrings.MODID.concat(":" + itemTex), RefStrings.MODID.concat(":" + blockTex), mat, dropWhenBroken);
//		setBlockTextureName(RefStrings.MODID.concat(":" + blockTex));
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_,
			int p_149646_5_)
	{
		return true;
	}
	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}
	@Override
	public int getRenderType()
	{
		return 41;
	}

}
