package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityCrateTungsten;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

//@invtweaks.api.container.ChestContainer
public class ContainerCrateTungsten extends Container {

	private TileEntityCrateTungsten crate;

	public ContainerCrateTungsten(InventoryPlayer invPlayer, TileEntityCrateTungsten te) {
		crate = te;

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(te, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 32 + i * 18 + (18 * 3)));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 90 + (18 * 3)));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= crate.getSizeInventory() - 1) {
				if(!this.mergeItemStack(var5, crate.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 0, crate.getSizeInventory(), false)) {
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
		return crate.isUseableByPlayer(player);
	}
}
