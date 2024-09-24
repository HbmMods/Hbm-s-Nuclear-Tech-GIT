package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;

public class ContainerLaunchPadRocket extends ContainerBase {

	public ContainerLaunchPadRocket(InventoryPlayer invPlayer, ISidedInventory machine) {
		super(invPlayer, machine);
		
		addSlotToContainer(new SlotNonRetarded(machine, 0, 37, 21)); // Rocket slot
		addSlotToContainer(new SlotNonRetarded(machine, 1, 37, 39)); // Drive slot
		
		addSlotToContainer(new SlotNonRetarded(machine, 2, 167, 90)); // Battery slot

		addSlotToContainer(new Slot(machine, 3, 77, 21)); // Input
		addSlotToContainer(new Slot(machine, 4, 77, 39)); // Output

		addSlots(invPlayer, 9, 14, 154, 3, 9); // Player inventory
		addSlots(invPlayer, 0, 14, 212, 1, 9); // Player hotbar
	}

}
