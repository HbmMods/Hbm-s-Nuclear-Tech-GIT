package com.hbm.inventory.container;

import com.hbm.tileentity.machine.storage.TileEntityFileCabinet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFileCabinet extends Container {
	
	protected TileEntityFileCabinet cabinet;
	
	public ContainerFileCabinet(InventoryPlayer invPlayer, TileEntityFileCabinet tile) {
		this.cabinet = tile;
		this.cabinet.openInventory();
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 4; j++) {
				this.addSlotToContainer(new Slot(tile, j + i * 4, 53 + j * 18, 18 + i * 36));
			}
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 88 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 146));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack returnStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack originalStack = slot.getStack();
			returnStack = originalStack.copy();

			if(index <= 7) {
				if(!this.mergeItemStack(originalStack, 8, this.inventorySlots.size(), true)) {
					return null;
				}
				
				slot.onSlotChange(originalStack, returnStack);
				
			} else if(!this.mergeItemStack(originalStack, 0, 8, false)) {
				return null;
			}

			if(originalStack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return returnStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return cabinet.isUseableByPlayer(player);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.cabinet.closeInventory();
	}
}
