package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityMachineMiniRTG;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineMiniRTG extends BlockContainer {

	public MachineMiniRTG(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineMiniRTG();
	}

	@Override
	public int getRenderType() {
		return MachineRTG.renderID;
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
