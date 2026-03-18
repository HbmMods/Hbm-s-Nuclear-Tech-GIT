package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.items.ModItems;
import com.hbm.util.InventoryUtil;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachinePlasmaForge extends ContainerBase {

	public ContainerMachinePlasmaForge(InventoryPlayer invPlayer, IInventory assembler) {
		super(invPlayer, assembler);

		// Battery
		this.addSlotToContainer(new SlotNonRetarded(assembler, 0, 152, 82));
		// Schematic
		this.addSlotToContainer(new SlotNonRetarded(assembler, 1, 35, 81));
		// Booster
		this.addSlotToContainer(new SlotNonRetarded(assembler, 2, 98, 116));
		// Input
		this.addSlots(assembler, 3, 8, 18, 3, 4);
		// Output
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, assembler, 15, 116, 36));
		
		this.playerInv(invPlayer, 8, 162);
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
				} else if(slotOriginal.getItem() == ModItems.blueprints) {
					if(!this.mergeItemStack(slotStack, 1, 2, false)) return null;
				} else {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, slotStack, 2, 15, false)) return null;
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
