package com.hbm.tileentity.machine;

import api.hbm.energy.IEnergyConsumer;

import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineDetector extends TileEntity implements IEnergyConsumer {
	
	long power;

	@Override
    public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
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
		return 20;
	}

}
