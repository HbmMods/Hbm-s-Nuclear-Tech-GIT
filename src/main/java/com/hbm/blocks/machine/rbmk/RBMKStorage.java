package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKOutgasser;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKStorage;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKStorage extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= this.offset)
			return new TileEntityRBMKStorage();
		
		return new TileEntityProxyCombo(true, false, false);
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDPassive;
	}
}
