package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.fusion.TileEntityFusionTorus;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFusionTorus extends Container {
	
	protected TileEntityFusionTorus torus;
	
	public ContainerFusionTorus(InventoryPlayer invPlayer, TileEntityFusionTorus tedf) {
		this.torus = tedf;

		this.addSlotToContainer(new SlotNonRetarded(torus, 0, 8, 82));
		this.addSlotToContainer(new SlotNonRetarded(torus, 1, 71, 81));
		this.addSlotToContainer(new SlotNonRetarded(torus, 2, 130, 36));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 35 + j * 18, 162 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 35 + i * 18, 220));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return torus.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack copy = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			copy = stack.copy();

			if(index <= 2) {
				if(!this.mergeItemStack(stack, 3, this.inventorySlots.size(), true)) return null;
			} else {
				
				if(copy.getItem() == ModItems.blueprints) {
					if(!this.mergeItemStack(stack, 1, 2, false)) return null;
				} else if(copy.getItem() instanceof IBatteryItem || copy.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 0, 1, false)) return null;
				} else {
					return null;
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
}
