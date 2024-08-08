package com.hbm.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerDriveProcessor extends ContainerBase {

	public ContainerDriveProcessor(InventoryPlayer invPlayer, IInventory machine) {
		super(invPlayer, machine);

		// 0 - active drive slot
		// 1 - cloning drive slot

		// 2 - upgrade slot

		// 3 - battery slot

		addSlotToContainer(new Slot(machine, 0, 30, 18));
		addSlotToContainer(new Slot(machine, 1, 50, 38));

		addSlotToContainer(new Slot(machine, 2, 81, 24));

		addSlotToContainer(new Slot(machine, 3, 134, 72));

		playerInv(invPlayer, 8, 125, 183);
	}
	
}
