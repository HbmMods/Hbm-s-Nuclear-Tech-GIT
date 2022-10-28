package com.hbm.tileentity.machine.storage;

import net.minecraft.inventory.ISidedInventory;

public class TileEntityCrateSteel extends TileEntityCrateBase implements ISidedInventory {

	public TileEntityCrateSteel() {
		super(54);
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.crateSteel";
	}
}
