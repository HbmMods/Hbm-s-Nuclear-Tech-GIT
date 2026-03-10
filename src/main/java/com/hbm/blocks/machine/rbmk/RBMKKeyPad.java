package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKKeyPad;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKKeyPad extends RBMKMiniPanelBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRBMKKeyPad();
	}
}
