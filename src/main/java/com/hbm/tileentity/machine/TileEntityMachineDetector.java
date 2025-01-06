package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energymk2.IEnergyReceiverMK2;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineDetector extends TileEntityLoadedBase implements IEnergyReceiverMK2 {
	
	long power;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			
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
	
	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
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
