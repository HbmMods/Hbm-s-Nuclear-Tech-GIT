package com.hbm.blocks.machine;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityTransporterRocket;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTransporter extends BlockContainer {

	public BlockTransporter(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTransporterRocket();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
			return player.getHeldItem() == null || player.getHeldItem().getItem() != ModItems.transporter_linker;
		
		if(player.getHeldItem() == null || player.getHeldItem().getItem() != ModItems.transporter_linker) {
			player.openGui(MainRegistry.instance, 0, world, x, y, z);
			return true;
		}
		
		return false;
	}
	
}
