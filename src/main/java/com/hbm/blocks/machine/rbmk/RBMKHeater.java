package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKHeater;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKHeater extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= this.offset)
			return new TileEntityRBMKHeater();

		return null;
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDControl;
	}
}
