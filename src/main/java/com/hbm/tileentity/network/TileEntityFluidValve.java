package com.hbm.tileentity.network;

import com.hbm.uninos.UniNodespace;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class TileEntityFluidValve extends TileEntityPipeBaseNT {

	@Override
	public boolean shouldCreateNode() {
		return this.getBlockMetadata() == 1;
	}

	public void updateState() {

		this.blockMetadata = -1; // delete cache

		if(this.getBlockMetadata() == 0 && this.node != null) {
			UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, this.getType().getNetworkProvider());
			this.node = null;
		}
	}

	@Override
	public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
		return oldBlock != newBlock;
	}
}
