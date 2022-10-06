package com.hbm.inventory.container;

import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.network.TileEntityCraneUnboxer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCraneUnboxer extends Container {
	
	protected TileEntityCraneUnboxer unboxer;
	
	public ContainerCraneUnboxer(InventoryPlayer invPlayer, TileEntityCraneUnboxer unboxer) {
		this.unboxer = unboxer;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 7; j++) {
				this.addSlotToContainer(new Slot(unboxer, j + i * 7, 8 + j * 18, 17 + i * 18));
			}
		}
		
		//upgrades
		this.addSlotToContainer(new SlotUpgrade(unboxer, 21, 152, 23));
		this.addSlotToContainer(new SlotUpgrade(unboxer, 22, 152, 47));

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
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= unboxer.getSizeInventory() - 1) {
				if(!this.mergeItemStack(var5, unboxer.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 0, unboxer.getSizeInventory(), false)) {
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return unboxer.isUseableByPlayer(player);
	}
}
