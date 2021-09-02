package com.hbm.tileentity.machine.pile;

public class TileEntityPileSource extends TileEntityPileBase {

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			for(int i = 0; i < 6; i++) {
				this.castRay(5, 5);
			}
		}
	}
}
