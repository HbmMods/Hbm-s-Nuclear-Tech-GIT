package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityMachinePrecAss;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachinePrecAss extends MachineAssemblyMachine {

	public MachinePrecAss(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachinePrecAss();
		return super.createNewTileEntity(world, meta);
	}
}
