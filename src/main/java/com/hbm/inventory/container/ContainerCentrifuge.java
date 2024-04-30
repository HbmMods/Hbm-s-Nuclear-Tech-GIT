package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCentrifuge extends Container {

	private TileEntityMachineCentrifuge diFurnace;

	public ContainerCentrifuge(InventoryPlayer invPlayer, TileEntityMachineCentrifuge tedf) {

		diFurnace = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 36, 50));
		this.addSlotToContainer(new Slot(tedf, 1, 9, 50));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 2, 63, 50));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 3, 83, 50));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 4, 103, 50));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 5, 123, 50));
		this.addSlotToContainer(new SlotUpgrade(tedf, 6, 149, 22));
		this.addSlotToContainer(new SlotUpgrade(tedf, 7, 149, 40));

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
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();
			
			
			if(index <= 7) {
				if(!this.mergeItemStack(stack, 8, this.inventorySlots.size(), true)) {
					return null;
				}
				
				slot.onSlotChange(stack, rStack);
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 1, 2, false))
						return null;
					
				} else if(rStack.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(stack, 6, 8, false))
						return null;
					
				} else if(!this.mergeItemStack(stack, 0, 1, false))
					return null;
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return rStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUseableByPlayer(player);
	}

}