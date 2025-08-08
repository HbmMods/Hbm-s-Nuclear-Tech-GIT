package com.hbm.tileentity.network;

import api.hbm.energymk2.Nodespace;

public class TileEntityCableSwitch extends TileEntityCableBaseNT {

	public void updateState() {
		
		//if the meta is 0 (OFF) and there is a net present, destroy and de-reference it.
		//that should be all, since the state being 0 also prevents the TE from updating and joining the new net.
		if(this.getBlockMetadata() == 0 && this.node != null) {
			Nodespace.destroyNode(worldObj, xCoord, yCoord, zCoord);
			this.node = null;
		}
	}
	
	@Override
	public boolean shouldCreateNode() {
		return this.getBlockMetadata() == 1;
	}
}
