package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityMachineSuperComputer;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineSuperComputer extends BlockDummyable {

	public MachineSuperComputer(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineSuperComputer();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {6, 0, 3, 3, 3, 3};
	}

	@Override
	public int getOffset() {
		return 3;
	}
}
