package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.TileEntityICF;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerICF extends Container {
	
	protected TileEntityICF icf;
	
	public ContainerICF(InventoryPlayer invPlayer, TileEntityICF tedf) {
		this.icf = tedf;
		
		for(int i = 0; i < 5; i++) this.addSlotToContainer(new Slot(icf, i, 80 + i * 18, 18));
		this.addSlotToContainer(new Slot(icf, 5, 116, 54));
		for(int i = 0; i < 5; i++) this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, icf, 6 + i, 80 + i * 18, 90));
		this.addSlotToContainer(new Slot(icf, 11, 44, 90));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 44 + j * 18, 140 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 44 + i * 18, 198));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack copy = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			copy = stack.copy();

			if(index <= 11) {
				if(!this.mergeItemStack(stack, 12, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(copy.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(stack, 11, 12, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(stack, 5, 6, false)) {
						if(!this.mergeItemStack(stack, 0, 5, false)) {
							return null;
						}
					}
				}
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return copy;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return icf.isUseableByPlayer(player);
	}
}
