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

public class ContainerMachinePUREX extends ContainerBase {

	public ContainerMachinePUREX(InventoryPlayer invPlayer, IInventory chemicalPlant) {
		super(invPlayer, chemicalPlant);

		// Battery
		this.addSlotToContainer(new SlotNonRetarded(chemicalPlant, 0, 152, 81));
		// Schematic
		this.addSlotToContainer(new SlotNonRetarded(chemicalPlant, 1, 35, 126));
		// Upgrades
		this.addSlots(chemicalPlant, 2, 152, 108, 2, 1);
		// Solid Input
		this.addSlots(chemicalPlant, 4, 8, 90, 1, 3);
		// Solid Output
		this.addOutputSlots(invPlayer.player, chemicalPlant, 7, 80, 36, 3, 2);
		
		this.playerInv(invPlayer, 8, 174);
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
					if(!this.mergeItemStack(slotStack, 1, 2, false)) return null;
				} else if(slotOriginal.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(slotStack, 2, 4, false)) return null;
				} else {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, slotStack, 4, 7, false)) return null;
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
