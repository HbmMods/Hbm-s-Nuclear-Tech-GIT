package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityStationPropulsionCreative;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockStationPropulsionCreative extends BlockContainer {

	public BlockStationPropulsionCreative(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityStationPropulsionCreative();
	}
	
}
