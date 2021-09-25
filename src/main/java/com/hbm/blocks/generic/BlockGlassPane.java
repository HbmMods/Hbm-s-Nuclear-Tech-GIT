package com.hbm.blocks.generic;

import com.hbm.interfaces.Untested;
import com.hbm.lib.RefStrings;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class BlockGlassPane extends BlockPane
{
	@Untested
	public BlockGlassPane(String itemTex, String blockTex, Material mat, boolean dropWhenBroken)
	{
		super(RefStrings.MODID.concat(":" + itemTex), RefStrings.MODID.concat(":" + blockTex), mat, dropWhenBroken);
		setBlockTextureName(RefStrings.MODID.concat(":" + blockTex));
	}

}
