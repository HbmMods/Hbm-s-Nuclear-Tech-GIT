package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.tileentity.machine.TileEntityMachineFunnel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFunnel extends Container {
	
	private TileEntityMachineFunnel funnel;

	public ContainerFunnel(InventoryPlayer playerInv, TileEntityMachineFunnel tile) {
		funnel = tile;

		for(int i = 0; i < 9; i++) this.addSlotToContainer(new Slot(tile, i, 8 + 18 * i, 18));
		for(int i = 0; i < 9; i++) this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, i + 9, 8 + 18 * i, 54));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 144));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return funnel.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(index);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(index <= 17) {
				if(!this.mergeItemStack(var5, 18, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 0, 9, false)) {
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
