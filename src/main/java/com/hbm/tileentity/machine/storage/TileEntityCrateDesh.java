package com.hbm.tileentity.machine.storage;

public class TileEntityCrateDesh extends TileEntityCrateBase {
	
	public TileEntityCrateDesh() {
		super(104); //8 rows with 13 slots
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.crateDesh";
	}
}
