package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.util.InventoryUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineAnnihilator extends ContainerBase {

	public ContainerMachineAnnihilator(InventoryPlayer invPlayer, IInventory annihilator) {
		super(invPlayer, annihilator);

		// Input
		this.addSlotToContainer(new SlotNonRetarded(annihilator, 0, 17, 45));
		// Fluid ID
		this.addSlotToContainer(new SlotNonRetarded(annihilator, 1, 35, 45));
		// Output
		this.addOutputSlots(invPlayer.player, annihilator, 2, 80, 36, 2, 3);
		
		this.playerInv(invPlayer, 8, 126);
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
				
				if(slotOriginal.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(slotStack, 0, 1, false)) return null;
				} else {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, slotStack, 1, 2, false)) return null;
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
