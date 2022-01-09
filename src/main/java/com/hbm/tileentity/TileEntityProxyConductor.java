package com.hbm.tileentity;

import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IPowerNet;
import net.minecraft.tileentity.TileEntity;

public class TileEntityProxyConductor extends TileEntityProxyBase implements IEnergyConductor {

	@Override
	public boolean canUpdate() {
		return false;
	}

	@Override
	public long transferPower(long power) {
		
		TileEntity te = this.getTE();
		
		if(te instanceof IEnergyConductor) {
			return ((IEnergyConductor)te).transferPower(power);
		}
		
		return 0;
	}

	@Override
	public long getPower() {
		
		TileEntity te = this.getTE();
		
		if(te instanceof IEnergyConductor) {
			return ((IEnergyConductor)te).getPower();
		}
		
		return 0;
	}

	@Override
	public long getMaxPower() {
		
		TileEntity te = this.getTE();
		
		if(te instanceof IEnergyConductor) {
			return ((IEnergyConductor)te).getMaxPower();
		}
		
		return 0;
	}

	@Override
	public IPowerNet getPowerNet() {
		
		TileEntity te = this.getTE();
		
		if(te instanceof IEnergyConductor) {
			return ((IEnergyConductor)te).getPowerNet();
		}
		
		return null;
	}

	@Override
	public void setPowerNet(IPowerNet network) {
		
		TileEntity te = this.getTE();
		
		if(te instanceof IEnergyConductor) {
			((IEnergyConductor)te).setPowerNet(network);
		}
	}
}
