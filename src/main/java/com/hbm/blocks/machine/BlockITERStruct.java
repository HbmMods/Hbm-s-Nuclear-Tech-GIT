package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityITERStruct;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockITERStruct extends BlockContainer {

	public BlockITERStruct(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityITERStruct();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
