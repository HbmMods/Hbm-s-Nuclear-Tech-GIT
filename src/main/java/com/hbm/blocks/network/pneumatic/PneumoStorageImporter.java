package com.hbm.blocks.network.pneumatic;

import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageImporter;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PneumoStorageImporter extends BlockMachineBase {

	public PneumoStorageImporter() {
		super(Material.iron, 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPneumoStorageImporter();
	}
}
