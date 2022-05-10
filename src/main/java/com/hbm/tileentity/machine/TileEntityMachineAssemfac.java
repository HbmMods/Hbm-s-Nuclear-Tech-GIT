package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

public class TileEntityMachineAssemfac extends TileEntityMachineBase {

	public TileEntityMachineAssemfac() {
		super(10 * 8 + 4 + 1); //8 assembler groups with 10 slots, 4 upgrade slots, 1 battery slot
	}

	@Override
	public String getName() {
		return "container.assemfac";
	}

	@Override
	public void updateEntity() {
		
	}
}
