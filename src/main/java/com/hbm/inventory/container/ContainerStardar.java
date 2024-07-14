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
		stardar = imGonnaThrowUpandShitAllovertheBeduggghhhh;
		
		//0 - battery
		//1-2 - upgrade slots
		
		//3-4 - fluid ID
		//5-10 - fluid I/O
		//11-13 - dissolved outputs
		
		//14 - crystal input
		//15 - niter input
		//16-17 - casting slots
		//18-23 - other material outputs
		
		this.addSlotToContainer(new Slot(imGonnaThrowUpandShitAllovertheBeduggghhhh, 0, 129, 124));	
		this.addSlotToContainer(new Slot(imGonnaThrowUpandShitAllovertheBeduggghhhh, 1, 109, 144));	
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
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return stardar.isUseableByPlayer(p_75145_1_);
	}

}
