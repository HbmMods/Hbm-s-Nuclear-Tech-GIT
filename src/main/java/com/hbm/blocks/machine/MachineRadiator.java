package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityRadiator;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineRadiator extends MachineCondenser {

	public MachineRadiator(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRadiator();
	}
	
}
