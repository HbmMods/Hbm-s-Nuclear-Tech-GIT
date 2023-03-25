package com.hbm.blocks.machine.pile;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.world.World;

public abstract class BlockGraphiteDrilledTE extends BlockGraphiteDrilledBase implements ITileEntityProvider {

	public BlockGraphiteDrilledTE() {
		super();
		this.isBlockContainer = true;
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
	
	/*@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int i, int j) {
		super.onBlockEventReceived(world, x, y, z, i, j);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null ? tileentity.receiveClientEvent(i, j) : false;
	}*/ //do we even need this? the TE doesn't implement it, so i guess not
}
