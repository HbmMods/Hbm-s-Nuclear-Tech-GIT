package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineAssembler extends BlockDummyable {

	public MachineAssembler(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineAssembler();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 2, 1, 2, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
}
