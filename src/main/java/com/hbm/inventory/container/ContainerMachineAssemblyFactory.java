package com.hbm.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

/**
 * ContainerMachineAssemblyFactory - STUB IMPLEMENTATION for compilation purposes
 */
public class ContainerMachineAssemblyFactory extends Container {

	public ContainerMachineAssemblyFactory(InventoryPlayer invPlayer, IInventory tile) {
		super();
	}

	@Override
	public net.minecraft.inventory.Slot addSlotToContainer(net.minecraft.inventory.Slot slotIn) {
		return super.addSlotToContainer(slotIn);
	}

	@Override
	public net.minecraft.item.ItemStack transferStackInSlot(net.minecraft.entity.player.EntityPlayer player, int index) {
		return null;
	}

	@Override
	public boolean canInteractWith(net.minecraft.entity.player.EntityPlayer player) {
		return true;
	}
}
