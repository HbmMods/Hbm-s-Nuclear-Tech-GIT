package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntitySpacer;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FractionSpacer extends BlockDummyable {

	public FractionSpacer(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntitySpacer();
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
}
