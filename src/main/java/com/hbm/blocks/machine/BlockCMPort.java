package com.hbm.blocks.machine;

import com.hbm.tileentity.TileEntityProxyCombo;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCMPort extends BlockCM implements ITileEntityProvider {

	public BlockCMPort(Material mat, Class<? extends Enum> theEnum, boolean multiName, boolean multiTexture) {
		super(mat, theEnum, multiName, multiTexture);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityProxyCombo().inventory().power().fluid();
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int m) {
		super.breakBlock(world, x, y, z, b, m);
		world.removeTileEntity(x, y, z);
	}
}
