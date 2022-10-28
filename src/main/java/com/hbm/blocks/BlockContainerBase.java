package com.hbm.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockContainerBase extends BlockBase implements ITileEntityProvider {

	protected BlockContainerBase(Material material) {
		super(material);
		this.isBlockContainer = true;
	}

	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		super.breakBlock(world, x, y, z, block, meta);
		world.removeTileEntity(x, y, z);
	}

	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventNo, int eventArg) {
		super.onBlockEventReceived(world, x, y, z, eventNo, eventArg);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null ? tileentity.receiveClientEvent(eventNo, eventArg) : false;
	}
}
