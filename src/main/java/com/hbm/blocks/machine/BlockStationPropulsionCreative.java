package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityStationPropulsionCreative;

import api.hbm.tile.IPropulsion;
import net.minecraft.block.Block;
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

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof IPropulsion) {
			((IPropulsion)te).unregisterPropulsion();
		}
		super.breakBlock(world, x, y, z, block, i);
	}
	
}
