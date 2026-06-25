package com.hbm.blocks.network.pneumatic;

import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageExporter;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PneumoStorageExporter extends BlockMachineBase {

	public PneumoStorageExporter() {
		super(Material.iron, 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPneumoStorageExporter();
	}
}
