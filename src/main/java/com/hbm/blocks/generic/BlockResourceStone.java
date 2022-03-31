package com.hbm.blocks.generic;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.BlockEnums;
import com.hbm.blocks.ModBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockResourceStone extends BlockEnumMulti {

	public BlockResourceStone() {
		super(Material.rock, BlockEnums.EnumStoneType.class, true, true);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
		
		if(meta == BlockEnums.EnumStoneType.ASBESTOS.ordinal()) {
			world.setBlock(x, y, z, ModBlocks.gas_asbestos);
		}
		
		super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
	}
}
