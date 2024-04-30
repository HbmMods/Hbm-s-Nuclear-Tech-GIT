package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityICF;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineICF extends BlockDummyable {

	public MachineICF() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityICF();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {5, 0, 1, 1, 8, 8};
	}

	@Override
	public int getOffset() {
		return 1;
	}
}
