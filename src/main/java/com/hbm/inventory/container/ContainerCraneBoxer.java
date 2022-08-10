package com.hbm.inventory.container;

import com.hbm.tileentity.network.TileEntityCraneBoxer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCraneBoxer extends Container {
	
	protected TileEntityCraneBoxer boxer;
	
	public ContainerCraneBoxer(InventoryPlayer invPlayer, TileEntityCraneBoxer boxer) {
		this.boxer = boxer;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 7; j++) {
				this.addSlotToContainer(new Slot(boxer, j + i * 7, 8 + j * 18, 17 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 103 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 161));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(slot);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(slot <= boxer.getSizeInventory() - 1) {
				if(!this.mergeItemStack(var5, boxer.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				if(!this.mergeItemStack(var5, 0, boxer.getSizeInventory(), false)) {
					 return null;
				}
				
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(player, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return boxer.isUseableByPlayer(player);
	}
}
