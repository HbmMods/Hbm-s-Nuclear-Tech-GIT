package com.hbm.inventory.container;

import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.TileEntityMachineCompressorBase;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCompressor extends Container {
	
	private TileEntityMachineCompressorBase compressor;

	public ContainerCompressor(InventoryPlayer playerInv, TileEntityMachineCompressorBase tile) {
		compressor = tile;
		
		//Fluid ID
		this.addSlotToContainer(new Slot(tile, 0, 17, 72));
		//Battery
		this.addSlotToContainer(new Slot(tile, 1, 152, 72));
		//Upgrades
		this.addSlotToContainer(new Slot(tile, 2, 52, 72));
		this.addSlotToContainer(new Slot(tile, 3, 70, 72));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 180));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return compressor.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(index);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(index < 4) {
				if(!this.mergeItemStack(var5, 4, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof IBatteryItem) {
					if(!this.mergeItemStack(var5, 1, 2, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 2, 4, false)) {
						return null;
					}
				}
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
		}

		return var3;
	}
}
