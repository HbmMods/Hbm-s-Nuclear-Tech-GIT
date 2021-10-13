package com.hbm.tileentity.network;

import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IPowerNet;
import api.hbm.energy.PowerNet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCableBaseNT extends TileEntity implements IEnergyConductor {
	
	private IPowerNet network;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote && canUpdate()) {
			
			//we got here either because the net doesn't exist or because it's not valid, so that's safe to assume
			this.setPowerNet(null);
			
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
			
			if(this.getPowerNet() == null) {
				new PowerNet().joinLink(this);
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		
		if(!worldObj.isRemote) {
			if(this.network != null) {
				this.network.destroy();
			}
		}
	}

	/**
	 * Only update until a power net is formed, in >99% of the cases it should be the first tick. Everything else is handled by neighbors and the net itself.
	 */
	@Override
	public boolean canUpdate() {
		return (this.network == null || !this.network.isValid()) && !this.isInvalid();
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return true;
	}

	@Override
	public long getPower() {
		return 0;
	}

	@Override
	public long getMaxPower() {
		return 0;
	}

	@Override
	public void setPowerNet(IPowerNet network) {
		this.network = network;
	}

	@Override
	public long transferPower(long power) {
		
		if(this.network == null)
			return power;
		
		return this.network.transferPower(power);
	}

	@Override
	public IPowerNet getPowerNet() {
		return this.network;
	}
}
