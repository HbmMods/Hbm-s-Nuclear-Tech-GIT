package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKModerator;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKModerator extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRBMKModerator();
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDPassive;
	}
}
