package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.tileentity.TileEntity;

public class TileEntityCore extends TileEntityMachineBase {

	public TileEntityCore() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.dfc_core";
	}

	@Override
	public void updateEntity() {
		
	}

}
