package com.hbm.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerTapeDrive extends ContainerBase {

	public ContainerTapeDrive(InventoryPlayer invPlayer, IInventory tedf) {
		super(invPlayer, tedf);
		
		this.addSlots(tedf, 0, 35, 27, 2, 6);
		
		this.playerInv(invPlayer, 8, 104);
	}
}
