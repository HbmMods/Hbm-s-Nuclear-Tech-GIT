package com.hbm.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLaunchPadRocket extends Container {
	
	private ISidedInventory machine;

	public ContainerLaunchPadRocket(InventoryPlayer invPlayer, ISidedInventory machine) {
		this.machine = machine;
		
		addSlotToContainer(new Slot(machine, 0, 8, 7)); // Rocket slot
		addSlotToContainer(new Slot(machine, 1, 7, 27)); // Drive slot
		
		addSlotToContainer(new Slot(machine, 2, 189, 81)); // Battery slot

		addSlotToContainer(new Slot(machine, 3, 135, 67)); // Input
		addSlotToContainer(new Slot(machine, 4, 135, 85)); // Output

		addSlots(invPlayer, 9, 8, 142, 3, 9); // Player inventory
		addSlots(invPlayer, 0, 8, 200, 1, 9); // Player hotbar
	}
	
	private void addSlots(IInventory inv, int from, int x, int y, int rows, int cols) {
		int slotSize = 18;
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				this.addSlotToContainer(new Slot(inv, col + row * cols + from, x + col * slotSize, y + row * slotSize));
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();

			if(index <= machine.getSizeInventory()) {
				return null;
			} else if(!this.mergeItemStack(slotStack, 0, machine.getSizeInventory(), false))
				return null;

			if(slotStack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return machine.isUseableByPlayer(player);
	}

}
