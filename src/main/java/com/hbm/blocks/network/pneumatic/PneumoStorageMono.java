package com.hbm.blocks.network.pneumatic;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PneumoStorageMono extends BlockContainer {

	public PneumoStorageMono() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

}