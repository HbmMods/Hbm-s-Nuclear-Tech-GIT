package com.hbm.inventory.container;

import com.hbm.util.InventoryUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTapeDrive extends ContainerBase {

	public ContainerTapeDrive(InventoryPlayer invPlayer, IInventory tedf) {
		super(invPlayer, tedf);
		
		this.addSlots(tedf, 0, 35, 27, 2, 6);
		
		this.playerInv(invPlayer, 8, 104);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack slotOriginal = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			slotOriginal = slotStack.copy();
			if(index <= tile.getSizeInventory() - 1) {
				if(!InventoryUtil.mergeItemStack(this.inventorySlots, slotStack, tile.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!InventoryUtil.mergeItemStack(this.inventorySlots, slotStack, 0, tile.getSizeInventory(), false)) {
				return null;
			}

			if(slotStack.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			slot.onPickupFromSlot(player, slotStack);
		}
		return slotOriginal;
	}
}