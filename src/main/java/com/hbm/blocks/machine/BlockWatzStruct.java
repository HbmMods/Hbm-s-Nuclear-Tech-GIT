package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityWatzStruct;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWatzStruct extends BlockContainer {

	public BlockWatzStruct(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWatzStruct();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
