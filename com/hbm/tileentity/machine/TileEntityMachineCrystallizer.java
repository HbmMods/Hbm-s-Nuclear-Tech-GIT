package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

public class TileEntityMachineCrystallizer extends TileEntityMachineBase {

	public TileEntityMachineCrystallizer() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.crystallizer";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
		}
	}

}
