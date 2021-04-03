package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControl;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKControlAuto extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRBMKControl();
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDControl;
	}
}
