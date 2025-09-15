package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBlueprints;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.util.InventoryUtil;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineAssemblyFactory extends ContainerBase {

	public ContainerMachineAssemblyFactory(InventoryPlayer invPlayer, IInventory assemFac) {
		super(invPlayer, assemFac);

		// Battery
		this.addSlotToContainer(new SlotNonRetarded(assemFac, 0, 234, 112));
		// Upgrades
		this.addSlots(assemFac, 1, 214, 149, 3, 1);
		
		for(int i = 0; i < 4; i++) {
			// Template
			this.addSlots(assemFac, 4 + i * 14, 25 + (i % 2) * 109, 54 + (i / 2) * 56, 1, 1);
			// Solid Input
			this.addSlots(assemFac, 5 + i * 14, 7 + (i % 2) * 109, 20 + (i / 2) * 56, 2, 6, 16);
			// Solid Output
			this.addOutputSlots(invPlayer.player, assemFac, 17 + i * 14, 87 + (i % 2) * 109, 54 + (i / 2) * 56, 1, 1);
		}
		
		this.playerInv(invPlayer, 33, 158);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack slotOriginal = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			slotOriginal = slotStack.copy();

			if(index <= tile.getSizeInventory() - 1) {
				SlotCraftingOutput.checkAchievements(player, slotStack);
				if(!this.mergeItemStack(slotStack, tile.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(slotOriginal.getItem() instanceof IBatteryItem || slotOriginal.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(slotStack, 0, 1, false)) return null;
				} else if(slotOriginal.getItem() instanceof ItemBlueprints) {
					if(!this.mergeItemStack(slotStack, 4, 5, false)) return null;
				} else if(slotOriginal.getItem() instanceof ItemBlueprints) {
					if(!this.mergeItemStack(slotStack, 18, 19, false)) return null;
				} else if(slotOriginal.getItem() instanceof ItemBlueprints) {
					if(!this.mergeItemStack(slotStack, 32, 33, false)) return null;
				} else if(slotOriginal.getItem() instanceof ItemBlueprints) {
					if(!this.mergeItemStack(slotStack, 44, 45, false)) return null;
				} else if(slotOriginal.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(slotStack, 1, 4, false)) return null;
				} else {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, slotStack, 5, 17, false) &&
							!InventoryUtil.mergeItemStack(this.inventorySlots, slotStack, 19, 31, false) &&
							!InventoryUtil.mergeItemStack(this.inventorySlots, slotStack, 33, 46, false) &&
							!InventoryUtil.mergeItemStack(this.inventorySlots, slotStack, 47, 59, false)) return null;
				}
			}

			if(slotStack.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			slot.onPickupFromSlot(player, slotStack);
		}

		return slotOriginal;
	}
}
