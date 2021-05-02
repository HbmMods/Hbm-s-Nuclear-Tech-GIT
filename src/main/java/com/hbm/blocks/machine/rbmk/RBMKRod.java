package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RBMKRod extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= this.offset)
			return new TileEntityRBMKRod();
		
		if(hasExtra(meta))
			return new TileEntityProxyInventory();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return openInv(world, x, y, z, player, ModBlocks.guiID_rbmk_rod);
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDRods;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		this.makeExtra(world, x, y + RBMKDials.getColumnHeight(world), z);
	}
}
