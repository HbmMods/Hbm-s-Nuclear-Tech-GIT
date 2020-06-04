package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineCrystallizer extends BlockMachineBase {

	public MachineCrystallizer(Material mat) {
		super(mat, ModBlocks.guiID_crystallizer);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineCrystallizer();
	}

}
