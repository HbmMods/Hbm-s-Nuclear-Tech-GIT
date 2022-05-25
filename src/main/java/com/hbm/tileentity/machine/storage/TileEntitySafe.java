package com.hbm.tileentity.machine.storage;

import net.minecraft.inventory.ISidedInventory;

public class TileEntitySafe extends TileEntityCrateBase implements ISidedInventory {

	public TileEntitySafe() {
		super(15);
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.safe";
	}
}
