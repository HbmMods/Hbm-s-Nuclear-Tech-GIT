package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineStardar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerStardar extends Container {
	
	TileEntityMachineStardar stardar;

	public ContainerStardar(InventoryPlayer player, TileEntityMachineStardar imGonnaThrowUpandShitAllovertheBeduggghhhh) {
		stardar = imGonnaThrowUpandShitAllovertheBeduggghhhh; // yummers
		
		this.addSlotToContainer(new Slot(imGonnaThrowUpandShitAllovertheBeduggghhhh, 0, 150, 124));	

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 174 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 232));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();

			if(index <= stardar.getSizeInventory()) {
				return null;
			} else if(!this.mergeItemStack(slotStack, 0, stardar.getSizeInventory(), false))
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
		return stardar.isUseableByPlayer(player);
	}

}
