package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachinePlasmaHeater extends BlockDummyable {

	public MachinePlasmaHeater() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 1, 1, 1, 8};
	}

	@Override
	public int getOffset() {
		return 1;
	}

}
