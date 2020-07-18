package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityITER;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineITER extends BlockDummyable {

	public MachineITER() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityITER();
	}

	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 7, 7, 7, 7};
	}

	@Override
	public int getOffset() {
		return 8;
	}

}
