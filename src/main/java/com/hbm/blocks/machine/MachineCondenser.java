package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityCondenser;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineCondenser extends BlockContainer {

	public MachineCondenser(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCondenser();
	}
}
