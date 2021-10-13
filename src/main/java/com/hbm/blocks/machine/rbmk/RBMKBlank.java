package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBlank;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKBlank extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= this.offset)
			return new TileEntityRBMKBlank();
		return null;
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDPassive;
	}
}
