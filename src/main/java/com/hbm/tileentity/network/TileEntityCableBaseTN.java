package com.hbm.tileentity.network;

import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IPowerNet;
import api.hbm.energy.PowerNet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCableBaseTN extends TileEntity implements IEnergyConductor {
	
	private IPowerNet network;

	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				
				TileEntity te = worldObj.getTileEntity(xCoord, yCoord, zCoord);
				
				if(te instanceof IEnergyConductor) {
					
					IEnergyConductor conductor = (IEnergyConductor) te;
					
					if(this.getPowerNet() == null) {
						this.setPowerNet(conductor.getPowerNet());
					} else if(conductor.getPowerNet() != null) {
						conductor.getPowerNet().join(this.getPowerNet());
					}
				}
			}
			
			if(this.getPowerNet() == null) {
				this.setPowerNet(new PowerNet().subscribe(this));
			}
		}
	}

	/**
	 * Only update until a power net is formed, in >99% of the cases it should be the first tick. Everything else is handled by neighbors and the net itself.
	 */
	@Override
	public boolean canUpdate() {
		return network == null;
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
		
	}

	@Override
	public long transferPower(long power) {
		return 0;
	}

	@Override
	public IPowerNet getPowerNet() {
		return null;
	}

}
