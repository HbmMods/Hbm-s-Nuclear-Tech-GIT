package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotSmelting;
import com.hbm.tileentity.machine.TileEntityFurnaceCombination;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFurnaceCombo extends Container {
	
	protected TileEntityFurnaceCombination furnace;
	
	public ContainerFurnaceCombo(InventoryPlayer invPlayer, TileEntityFurnaceCombination furnace) {
		this.furnace = furnace;

		//input
		this.addSlotToContainer(new Slot(furnace, 0, 26, 36));
		//output
		this.addSlotToContainer(new SlotSmelting(invPlayer.player, furnace, 1, 89, 36));
		this.addSlotToContainer(new Slot(furnace, 2, 136, 18));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, furnace, 3, 136, 54));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 104 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 162));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack originalStack = slot.getStack();
			stack = originalStack.copy();

			if(index <= 3) {
				if(!this.mergeItemStack(originalStack, 4, this.inventorySlots.size(), true)) {
					return null;
				}
				
				slot.onSlotChange(originalStack, stack);
				
			} else if(!this.mergeItemStack(originalStack, 0, 1, false)) {
				return null;
			}

			if(originalStack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return furnace.isUseableByPlayer(player);
	}
}
