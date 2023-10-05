package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineArcWelder;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineArcWelder extends BlockDummyable {

	public MachineArcWelder(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineArcWelder();
		return new TileEntityProxyCombo().inventory().power().fluid();
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 1, 0, 1, 1};
	}

	@Override
	public int getOffset() {
		return 0;
	}
}
