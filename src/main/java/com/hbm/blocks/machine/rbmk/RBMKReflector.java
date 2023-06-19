package com.hbm.blocks.machine.rbmk;

import com.hbm.config.GeneralConfig;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKReflector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKReflector extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= this.offset)
			return new TileEntityRBMKReflector();
		return null;
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDPassive;
	}
	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {

		if(tool != ToolType.SCREWDRIVER && tool != ToolType.HAND_DRILL && !GeneralConfig.enableAdjustableNeutronReflector)
			return false;

		if(world.isRemote) return true;

		int[] pos = this.findCore(world, x, y, z);

		if(pos == null) return false;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntityRBMKReflector)) return false;

		TileEntityRBMKReflector tile = (TileEntityRBMKReflector) te;
		tile.modeSetting();
		tile.markDirty();


		return true;
	}
}
