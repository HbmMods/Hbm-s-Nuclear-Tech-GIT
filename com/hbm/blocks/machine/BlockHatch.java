package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityHatch;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHatch extends BlockContainer {

	public BlockHatch(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityHatch();
	}
	
	public void setControllerPos(World world, int x, int y, int z, int cX, int cY, int cZ) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te != null && te instanceof TileEntityHatch) {
			((TileEntityHatch)te).setControllerPos(cX, cY, cZ);
		}
		
	}

}
