package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityDeaerator;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineDeaerator extends BlockDummyable {

	public MachineDeaerator(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityDeaerator();
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 3, 4, 1, 1};
	}

	@Override
	public int getOffset() {
		return 0;
	}

}
