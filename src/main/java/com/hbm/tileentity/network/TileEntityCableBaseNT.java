package com.hbm.tileentity.network;

import api.hbm.energymk2.IEnergyConductorMK2;
import api.hbm.energymk2.Nodespace;
import api.hbm.energymk2.Nodespace.PowerNode;
import com.hbm.tileentity.TileEntityLoadedBase;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCableBaseNT extends TileEntityLoadedBase implements IEnergyConductorMK2 {

	protected PowerNode node;

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(this.node == null || this.node.expired) {

				if(this.shouldCreateNode()) {
					this.node = Nodespace.getNode(worldObj, xCoord, yCoord, zCoord);

					if(this.node == null || this.node.expired) {
						this.node = this.createNode();
						Nodespace.createNode(worldObj, this.node);
					}
				}
			}
		}
	}

	public boolean shouldCreateNode() {
		return true;
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(this.node != null) {
				Nodespace.destroyNode(worldObj, xCoord, yCoord, zCoord);
			}
		}
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}
}
