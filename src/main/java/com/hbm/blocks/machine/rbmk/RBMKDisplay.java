package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKDisplay;

import api.hbm.block.IToolable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKDisplay extends RBMKMiniPanelBase implements IToolable {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRBMKDisplay();
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.SCREWDRIVER)
			return false;
		if(!world.isRemote) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileEntityRBMKDisplay) {
				((TileEntityRBMKDisplay) tile).rotate();
			}
		}
		return true;
	}
}
