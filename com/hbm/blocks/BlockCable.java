package com.hbm.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCable extends BlockContainer {

	protected BlockCable(Material p_i45386_1_) {
		super(p_i45386_1_);
		float p = 1F/16F;
		this.setBlockBounds(11 * p / 2, 11 * p / 2, 11 * p / 2, 1 - 11 * p / 2, 1 - 11 * p / 2, 1 - 11 * p / 2);
		this.useNeighborBrightness = true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCable();
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
