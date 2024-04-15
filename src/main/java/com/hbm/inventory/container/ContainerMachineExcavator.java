package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemDrillbit;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineExcavator;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineExcavator extends Container {
	
	TileEntityMachineExcavator excavator;

	public ContainerMachineExcavator(InventoryPlayer invPlayer, TileEntityMachineExcavator tile) {
		this.excavator = tile;

		//Battery: 0
		this.addSlotToContainer(new Slot(tile, 0, 220, 72));
		//Fluid ID: 1
		this.addSlotToContainer(new Slot(tile, 1, 202, 72));
		//Upgrades: 2-4
		for(int i = 0; i < 3; i++) {
			this.addSlotToContainer(new Slot(tile, 2 + i, 136 + i * 18, 75));
		}
		
		//Buffer: 5-13
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new SlotTakeOnly(tile, 5 + j + i * 3, 136 + j * 18, 5 + i * 18));
			}
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 41 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 41 + i * 18, 180));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();

			if(index <= 13) {
				if(!this.mergeItemStack(stack, 14, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 0, 1, false)) return null;
				} else if(rStack.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(stack, 1, 2, false)) return null;
				} else if(rStack.getItem() instanceof ItemMachineUpgrade ) {
					if(!this.mergeItemStack(stack, 2, 4, false)) return null;
				} else if(rStack.getItem() instanceof ItemDrillbit) {
					if(!this.mergeItemStack(stack, 4, 5, false)) return null;
				} else
					return null;
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return rStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return excavator.isUseableByPlayer(player);
	}
}
