package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRod;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKRod extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRBMKRod();
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDRods;
	}
}
