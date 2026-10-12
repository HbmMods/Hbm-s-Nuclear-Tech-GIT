package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.TilePortShapes;
import com.hbm.tileentity.TilePort.PortDef;

import api.hbm.energymk2.IEnergyReceiverMK2;

public class TileEntityMachineDetector extends TileEntityLoadedBase implements IEnergyReceiverMK2 {
	
	long power;
	
	protected PortDef[] cachedPorts;
	public PortDef[] getPorts() { if(cachedPorts == null) cachedPorts = TilePortShapes.around(xCoord, yCoord, zCoord); return cachedPorts; }

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.setupPowerPorts(getPorts());
			this.updateAllPorts();
			this.receivePower();
			
			int meta = this.getBlockMetadata();
			int state = 0;
			
			if(power > 0) {
				state = 1;
				power--;
			}
			
			if(meta != state) {
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, state, 3);
				this.markDirty();
			}
		}
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return 5;
	}

	@Override
	public ConnectionPriority getPriority() {
		return ConnectionPriority.HIGH;
	}
}
