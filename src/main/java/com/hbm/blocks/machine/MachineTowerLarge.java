package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityTowerLarge;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineTowerLarge extends BlockDummyable {

	public MachineTowerLarge(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		
		if(meta >= 12)
			return new TileEntityTowerLarge();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {12, 0, 4, 4, 4, 4};
	}

	@Override
	public int getOffset() {
		return 4;
	}
}
