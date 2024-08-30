package com.hbm.tileentity.network;

import api.hbm.fluid.PipeNet;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class TileEntityFluidValve extends TileEntityPipeBaseNT {
	
	@Override
	public boolean shouldConnect() {
		return this.worldObj != null && this.getBlockMetadata() == 1 && super.canUpdate();
	}

	public void updateState() {
		
		this.blockMetadata = -1; // delete cache
		
		if(this.getBlockMetadata() == 0 && this.network != null) {
			this.network.destroy();
			this.network = null;
		}
		
		if(this.getBlockMetadata() == 1) {
			this.connect();
			
			if(this.getPipeNet(type) == null) {
				new PipeNet(type).joinLink(this);
			}
		}
	}
	
	@Override
	public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
		return oldBlock != newBlock;
	}
}
