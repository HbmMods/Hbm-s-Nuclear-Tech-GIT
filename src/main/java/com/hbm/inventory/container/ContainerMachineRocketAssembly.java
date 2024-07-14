package com.hbm.inventory.container;

import com.hbm.handler.RocketStruct;
import com.hbm.inventory.SlotRocket;
import com.hbm.inventory.SlotTakeOnly;
import com.hbm.inventory.SlotRocket.SlotCapsule;
import com.hbm.inventory.SlotRocket.SlotDrive;
import com.hbm.items.weapon.ItemCustomMissilePart.PartType;

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
		addSlotToContainer(new SlotCapsule(machine, slotId++, 18, 13));

		// Stages
		for(int i = 0; i < RocketStruct.MAX_STAGES; i++) {
			addSlotToContainer(new SlotRocket(machine, slotId++, 18, 44, i, PartType.FUSELAGE));
			addSlotToContainer(new SlotRocket(machine, slotId++, 18, 62, i, PartType.FINS));
			addSlotToContainer(new SlotRocket(machine, slotId++, 18, 80, i, PartType.THRUSTER));
		}

		// Result
		addSlotToContainer(new SlotTakeOnly(machine, slotId++, 42, 91));

		// Drives
		addSlotToContainer(new SlotDrive(machine, slotId++, 165, 54));
		addSlotToContainer(new SlotDrive(machine, slotId++, 165, 87));

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
