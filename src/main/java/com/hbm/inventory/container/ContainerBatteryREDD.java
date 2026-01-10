package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.tileentity.machine.storage.TileEntityBatteryREDD;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBatteryREDD extends Container {
	
	protected TileEntityBatteryREDD socket;
	
	public ContainerBatteryREDD(InventoryPlayer invPlayer, TileEntityBatteryREDD tedf) {
		this.socket = tedf;

		this.addSlotToContainer(new SlotNonRetarded(socket, 0, 26, 53));
		this.addSlotToContainer(new SlotNonRetarded(socket, 1, 80, 53));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 99 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 157));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return socket.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack copy = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			copy = stack.copy();

			if(index <= 1) {
				if(!this.mergeItemStack(stack, 2, this.inventorySlots.size(), true)) return null;
			} else {
				if(!this.mergeItemStack(stack, 0, 2, false)) return null;
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return copy;
	}
}
