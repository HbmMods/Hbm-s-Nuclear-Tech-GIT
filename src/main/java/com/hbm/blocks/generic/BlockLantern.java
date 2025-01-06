package com.hbm.blocks.generic;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.deco.TileEntityLantern;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLantern extends BlockDummyable {

	public BlockLantern() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityLantern();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}
}
