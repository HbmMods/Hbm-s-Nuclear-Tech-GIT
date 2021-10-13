package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKAbsorber;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKAbsorber extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= this.offset)
			return new TileEntityRBMKAbsorber();
		return null;
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDPassive;
	}
}
