package com.hbm.tileentity;

import com.hbm.interfaces.IConsumer;

import net.minecraft.tileentity.TileEntity;

//can be used as a soruce too since the core TE handles that anyway
public class TileEntityProxyEnergy extends TileEntityProxyBase implements IConsumer {
	
    public boolean canUpdate()
    {
        return false;
    }

	@Override
	public void setPower(long i) {
		
		TileEntity te = getTE();
		
		if(te instanceof IConsumer) {
			((IConsumer)te).setPower(i);
		}
	}

	@Override
	public long getPower() {
		
		TileEntity te = getTE();
		
		if(te instanceof IConsumer) {
			return ((IConsumer)te).getPower();
		}
		
		return 0;
	}

	@Override
	public long getMaxPower() {
		
		TileEntity te = getTE();
		
		if(te instanceof IConsumer) {
			return ((IConsumer)te).getMaxPower();
		}
		
		return 0;
	}
}
