package com.hbm.blocks.generic;

import com.hbm.interfaces.IBlockRadShield;
import com.hbm.interfaces.Untested;
import com.hbm.modules.BlockRadShieldModule;

import net.minecraft.block.material.Material;
@Untested
public class BlockRadShield extends BlockGeneric implements IBlockRadShield
{
	public BlockRadShield(Material mat)
	{
		super(mat);
	}
}
