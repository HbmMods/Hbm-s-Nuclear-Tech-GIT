package com.hbm.inventory.container;

import com.hbm.inventory.SlotLayer;
import com.hbm.inventory.SlotTakeOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineRocketAssembly extends Container {

	private ISidedInventory machine;

	public ContainerMachineRocketAssembly(InventoryPlayer invPlayer, ISidedInventory machine) {
		this.machine = machine;

		int slotId = 0;

		// Capsule slot
		this.addSlotToContainer(new Slot(machine, slotId++, 18, 13));

		// Stages
		for(int i = 0; i < 5; i++) {
			this.addSlotToContainer(new SlotLayer(machine, slotId++, 18, 44, i));
			this.addSlotToContainer(new SlotLayer(machine, slotId++, 18, 62, i));
			this.addSlotToContainer(new SlotLayer(machine, slotId++, 18, 80, i));
		}

		this.addSlotToContainer(new SlotTakeOnly(machine, slotId++, 42, 91));

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
