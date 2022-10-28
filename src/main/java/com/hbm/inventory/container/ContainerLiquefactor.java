package com.hbm.inventory.container;

import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.machine.oil.TileEntityMachineLiquefactor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLiquefactor extends Container {
	
	private TileEntityMachineLiquefactor liquefactor;

	public ContainerLiquefactor(InventoryPlayer playerInv, TileEntityMachineLiquefactor tile) {
		liquefactor = tile;
		
		//Input
		this.addSlotToContainer(new Slot(tile, 0, 35, 54));
		//Battery
		this.addSlotToContainer(new Slot(tile, 1, 134, 72));
		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(tile, 2, 98, 36));
		this.addSlotToContainer(new SlotUpgrade(tile, 3, 98, 54));

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
		return liquefactor.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(index);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(index <= 3) {
				if(!this.mergeItemStack(var5, 4, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 0, 3, false)) {
				return null;
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
