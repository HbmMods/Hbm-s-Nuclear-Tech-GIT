package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKReflector;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKReflector extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= this.offset)
			return new TileEntityRBMKReflector();
		return null;
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDPassive;
	}
}
