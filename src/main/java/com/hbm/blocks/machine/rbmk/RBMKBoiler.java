package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBoiler;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKBoiler extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= this.offset)
			return new TileEntityRBMKBoiler();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
			
		} else if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_rbmk_boiler, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDControl;
	}
}
