package com.hbm.tileentity;

import api.hbm.energy.IEnergyConsumer;

import net.minecraft.tileentity.TileEntity;

//can be used as a soruce too since the core TE handles that anyway
public class TileEntityProxyEnergy extends TileEntityProxyBase implements IEnergyConsumer {
	
    public boolean canUpdate()
    {
        return false;
    }

	@Override
	public void setPower(long i) {
		
		TileEntity te = getTE();
		
		if(te instanceof IEnergyConsumer) {
			((IEnergyConsumer)te).setPower(i);
		}
	}

	@Override
	public long getPower() {
		
		TileEntity te = getTE();
		
		if(te instanceof IEnergyConsumer) {
			return ((IEnergyConsumer)te).getPower();
		}
		
		return 0;
	}

	@Override
	public long getMaxPower() {
		
		TileEntity te = getTE();
		
		if(te instanceof IEnergyConsumer) {
			return ((IEnergyConsumer)te).getMaxPower();
		}
		
		return 0;
	}
}
