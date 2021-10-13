package com.hbm.blocks.generic;

import com.hbm.interfaces.IBlockRadShield;
import com.hbm.interfaces.IBlockRarity;
import com.hbm.items.block.ItemBlockLore;
import com.hbm.modules.BlockRadShieldModule;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

	BlockRadShieldModule sModule;
public class BlockGeneric extends Block {

	public BlockGeneric(Material material) {
		super(material);
	}
	
	@Override
	public BlockRadShieldModule getShieldModule()
	{
		return sModule;
	}
}
