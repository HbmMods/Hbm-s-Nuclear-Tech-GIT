package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnaceLarge;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineArcFurnaceLarge extends BlockDummyable {

	public MachineArcFurnaceLarge() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMachineArcFurnaceLarge();
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}
}
