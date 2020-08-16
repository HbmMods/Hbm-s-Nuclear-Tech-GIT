package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityMachinePlasmaHeater;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachinePlasmaHeater extends BlockDummyable {

	public MachinePlasmaHeater() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityMachinePlasmaHeater();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 8, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

}
