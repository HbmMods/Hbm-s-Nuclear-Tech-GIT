package com.hbm.tileentity.machine.storage;

public class TileEntityCrateIron extends TileEntityCrateBase {

	public TileEntityCrateIron() {
		super(36);
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.crateIron";
	}
}
