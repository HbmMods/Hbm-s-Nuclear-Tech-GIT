package com.hbm.blocks.network;

import com.hbm.tileentity.network.TileEntityPneumoTube;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PneumoTube extends BlockContainer {

	public PneumoTube() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPneumoTube();
	}
}
