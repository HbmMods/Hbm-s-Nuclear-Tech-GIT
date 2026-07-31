package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

public class TileEntityMachineRockMill extends TileEntityMachineBase {
	
	public float rotation;
	public float prevRotation;
	
	public boolean frame = false;

	public TileEntityMachineRockMill() {
		super(0);
	}

	@Override
	public String getName() {
		return "container.machineRockMill";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
		} else {

			if(worldObj.getTotalWorldTime() % 20 == 0) {
				frame = !worldObj.getBlock(xCoord, yCoord + 3, zCoord).isAir(worldObj, xCoord, yCoord + 3, zCoord);
			}
		}
	}
}
