package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityMachineSchrabidiumTransmutator;
import com.hbm.util.InventoryUtil;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineSchrabidiumTransmutator extends Container {

	private TileEntityMachineSchrabidiumTransmutator tile;
	
	public ContainerMachineSchrabidiumTransmutator(InventoryPlayer invPlayer, TileEntityMachineSchrabidiumTransmutator tedf) {
		
		tile = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 44, 63));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 1, 134, 63));
		this.addSlotToContainer(new Slot(tedf, 2, 26, 18));
		this.addSlotToContainer(new Slot(tedf, 3, 8, 108));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
		}
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();
			
            if (index <= 3) {
				if (!this.mergeItemStack(stack, 4, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {

				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, stack, 3, 4, false)) return null;
				} else if(rStack.getItem() == ModItems.redcoil_capacitor || rStack.getItem() == ModItems.euphemium_capacitor) {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, stack, 2, 3, false)) return null;
				} else {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, stack, 0, 1, false)) return null;
				}
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
		return tile.isUseableByPlayer(player);
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1) {
			tile.power = j;
		}
	}
}
