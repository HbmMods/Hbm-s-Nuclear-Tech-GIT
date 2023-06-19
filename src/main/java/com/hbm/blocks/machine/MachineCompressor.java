package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityMachineCompressor;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineCompressor extends BlockDummyable {

	public MachineCompressor() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineCompressor();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 1, 2, 1, 1};
	}

	@Override
	public int getOffset() {
		return 2;
	}
}
