package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityMicrowave;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineMicrowave extends BlockMachineBase {

	public MachineMicrowave(Material mat) {
		super(mat, 0);
		this.rotatable = true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMicrowave();
	}
	
	@Override
	public int getRenderType(){
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
