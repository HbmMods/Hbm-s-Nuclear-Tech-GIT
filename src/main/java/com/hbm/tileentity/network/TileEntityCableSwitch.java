package com.hbm.tileentity.network;

import api.hbm.energy.PowerNet;

public class TileEntityCableSwitch extends TileEntityCableBaseNT {
	
	@Override
	public boolean canUpdate() {
		return this.worldObj != null && this.getBlockMetadata() == 1 && super.canUpdate();
	}

	public void updateState() {
		
		//if the meta is 0 (OFF) and there is a net present, destroy and de-reference it.
		//that should be all, since the state being 0 also prevents the TE from updating and joining the new net.
		if(this.getBlockMetadata() == 0 && this.network != null) {
			this.network.reevaluate();
			this.network = null;
		}
		
		if(this.getBlockMetadata() == 1) {
			this.connect();
			
			if(this.getPowerNet() == null) {
				new PowerNet().joinLink(this);
			}
		}
	}
	
	public boolean canReevaluate() {
		return super.canReevaluate() && this.getBlockMetadata() == 1;
	}
}
