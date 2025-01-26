package com.hbm.inventory.container;

import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityFEL;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFEL extends Container {

	private TileEntityFEL fel;

	public ContainerFEL(InventoryPlayer invPlayer, TileEntityFEL tedf) {

		fel = tedf;

		//battery
		this.addSlotToContainer(new Slot(tedf, 0, 182, 144));
		//laser crystal
		this.addSlotToContainer(new Slot(tedf, 1, 141, 23));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 83 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 141));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();

			if(index <= 1) {
				if(!this.mergeItemStack(stack, 2, this.inventorySlots.size(), false)) {
					return null;
				}
			} else {
				
				if(rStack.getItem() instanceof api.hbm.energymk2.IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 0, 1, false)) return null;
				} else {
					if(!this.mergeItemStack(stack, 1, 2, false)) return null;
				}
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
		return fel.isUseableByPlayer(player);
	}
}