package com.hbm.tileentity.network;

import api.hbm.fluid.PipeNet;

public class TileEntityFluidValve extends TileEntityPipeBaseNT {
	
	@Override
	public boolean canUpdate() {
		return this.worldObj != null && this.getBlockMetadata() == 1 && super.canUpdate();
	}

	public void updateState() {
		
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
}
