package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKConsole extends BlockMachineBase {

	public RBMKConsole() {
		super(Material.iron, 0);
		this.rotatable = true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityRBMKConsole();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
