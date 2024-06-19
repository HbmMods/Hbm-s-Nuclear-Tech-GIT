package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineArcWelder;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineArcWelder extends Container {
	
	private TileEntityMachineArcWelder welder;

	public ContainerMachineArcWelder(InventoryPlayer playerInv, TileEntityMachineArcWelder tile) {
		welder = tile;
		
		//Inputs
		this.addSlotToContainer(new Slot(tile, 0, 17, 36));
		this.addSlotToContainer(new Slot(tile, 1, 35, 36));
		this.addSlotToContainer(new Slot(tile, 2, 53, 36));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 3, 107, 36));
		//Battery
		this.addSlotToContainer(new Slot(tile, 4, 152, 72));
		//Fluid ID
		this.addSlotToContainer(new Slot(tile, 5, 17, 63));
		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(tile, 6, 89, 63));
		this.addSlotToContainer(new SlotUpgrade(tile, 7, 107, 63));

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
		return welder.isUseableByPlayer(player);
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
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 4, 5, false)) return null;
				} else if(rStack.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(stack, 5, 6, false)) return null;
				} else if(rStack.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(stack, 6, 8, false)) return null;
				} else {
					if(!this.mergeItemStack(stack, 0, 3, false)) return null;
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
}
