package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBoiler;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKBoiler extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRBMKBoiler();
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDControl;
	}
}
