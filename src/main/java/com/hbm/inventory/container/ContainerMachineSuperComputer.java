package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.inventory.container.ContainerBase;
import com.hbm.items.ModItems;
import com.hbm.util.InventoryUtil;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineSuperComputer extends ContainerBase {

	public ContainerMachineSuperComputer(InventoryPlayer invPlayer, IInventory computer) {
		super(invPlayer, computer);

		// Battery
		this.addSlotToContainer(new SlotNonRetarded(computer, 0, 152, 81));
		// Schematic
		this.addSlotToContainer(new SlotNonRetarded(computer, 1, 35, 80));
		// Input
		this.addSlots(computer, 2, 8, 27, 1, 3);
		// Output
		this.addOutputSlots(invPlayer.player, computer, 5, 80, 27, 1, 3);
		
		this.playerInv(invPlayer, 8, 129);
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
				
				if(slotOriginal.getItem() instanceof IBatteryItem) {
					if(!this.mergeItemStack(slotStack, 0, 1, false)) return null;
				} else if(slotOriginal.getItem() == ModItems.blueprints) {
					if(!this.mergeItemStack(slotStack, 1, 2, false)) return null;
				} else {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, slotStack, 2, 5, false)) return null;
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
