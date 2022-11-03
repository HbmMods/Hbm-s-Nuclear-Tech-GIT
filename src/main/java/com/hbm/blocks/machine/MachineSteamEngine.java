package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntitySteamEngine;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineSteamEngine extends BlockDummyable {

	public MachineSteamEngine() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntitySteamEngine();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 5, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
}
