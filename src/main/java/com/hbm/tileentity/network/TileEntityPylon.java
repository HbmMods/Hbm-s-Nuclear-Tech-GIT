package com.hbm.tileentity.network;

import api.hbm.energy.IEnergyConductor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPylon extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.SINGLE;
	}

	@Override
	public Vec3 getMountPos() {
		return Vec3.createVectorHelper(xCoord + 0.5, yCoord + 5.4, zCoord + 0.5);
	}

	@Override
	public double getMaxWireLength() {
		return 25D;
	}
	
	@Override
	protected void connect() {
		
		/*
		 * Apparently super.super does not exist, and the mentally damaged folk from heckoverflow pretend like that's a good thing.
		 * Look at this shit, you think that's good? "Write Everything Twice"? You like that, huh?
		 */
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			
			if(te instanceof IEnergyConductor) {
				
				IEnergyConductor conductor = (IEnergyConductor) te;
				
				if(this.getPowerNet() == null && conductor.getPowerNet() != null) {
					conductor.getPowerNet().joinLink(this);
				}
				
				if(this.getPowerNet() != null && conductor.getPowerNet() != null && this.getPowerNet() != conductor.getPowerNet()) {
					conductor.getPowerNet().joinNetworks(this.getPowerNet());
				}
			}
		}
		
		super.connect();
	}
}
