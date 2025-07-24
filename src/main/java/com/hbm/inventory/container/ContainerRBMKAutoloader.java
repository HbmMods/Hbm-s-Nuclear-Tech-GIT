package com.hbm.inventory.container;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKAutoloader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRBMKAutoloader extends ContainerBase {
	
	public TileEntityRBMKAutoloader loader;
	
	public ContainerRBMKAutoloader(InventoryPlayer invPlayer, TileEntityRBMKAutoloader tedf) {
		super(invPlayer, tedf);
		loader = tedf;

		this.addSlots(loader, 0, 17, 18, 3, 3);
		this.addTakeOnlySlots(loader, 9, 107, 18, 3, 3);
		this.playerInv(invPlayer, 8, 100);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();

			if(index <= 17) {
				if(!this.mergeItemStack(stack, 18, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				if(!this.mergeItemStack(stack, 0, 9, false)) return null;
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
		return loader.isUseableByPlayer(player);
	}
}
