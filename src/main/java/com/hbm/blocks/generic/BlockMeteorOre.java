package com.hbm.blocks.generic;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.BlockEnums;

import net.minecraft.block.material.Material;

public class BlockMeteorOre extends BlockEnumMulti {

	public BlockMeteorOre() {
		super(Material.rock, BlockEnums.EnumMeteorType.class, true, true);
	}
}
