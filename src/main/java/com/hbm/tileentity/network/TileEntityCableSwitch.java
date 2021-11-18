package com.hbm.tileentity.network;

import net.minecraft.tileentity.TileEntity;

public class TileEntityCableSwitch extends TileEntityCableBaseNT {
	
	@Override
	public boolean canUpdate() {
		//only update if the meta is 1 (ON), updating causes the net to form and allows transmission
		return this.getBlockMetadata() == 1 && super.canUpdate();
	}

	public void updateState() {
		
		//if the meta is 0 (OFF) and there is a net present, destroy and de-reference it.
		//that should be all, since the state being 0 also prevents the TE from updating and joining the new net.
		if(this.getBlockMetadata() == 0 && this.network != null) {
			this.network.destroy();
			this.network = null;
		}
	}
}
