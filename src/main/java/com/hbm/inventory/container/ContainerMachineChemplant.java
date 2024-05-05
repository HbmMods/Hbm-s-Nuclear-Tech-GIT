package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineChemplant extends Container {

	private TileEntityMachineChemplant nukeBoy;

	public ContainerMachineChemplant(InventoryPlayer invPlayer, TileEntityMachineChemplant tedf) {
		nukeBoy = tedf;

		// Battery
		this.addSlotToContainer(new Slot(tedf, 0, 80, 18));
		// Upgrades
		this.addSlotToContainer(new Slot(tedf, 1, 116, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 116, 36));
		this.addSlotToContainer(new Slot(tedf, 3, 116, 54));
		// Schematic
		this.addSlotToContainer(new Slot(tedf, 4, 80, 54));
		// Outputs
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 5, 134, 90));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 6, 152, 90));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 7, 134, 108));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 8, 152, 108));
		// Fluid Output In
		this.addSlotToContainer(new Slot(tedf, 9, 134, 54));
		this.addSlotToContainer(new Slot(tedf, 10, 152, 54));
		// Fluid Outputs Out
		this.addSlotToContainer(new SlotTakeOnly(tedf, 11, 134, 72));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 12, 152, 72));
		// Input
		this.addSlotToContainer(new Slot(tedf, 13, 8, 90));
		this.addSlotToContainer(new Slot(tedf, 14, 26, 90));
		this.addSlotToContainer(new Slot(tedf, 15, 8, 108));
		this.addSlotToContainer(new Slot(tedf, 16, 26, 108));
		// Fluid Input In
		this.addSlotToContainer(new Slot(tedf, 17, 8, 54));
		this.addSlotToContainer(new Slot(tedf, 18, 26, 54));
		// Fluid Input Out
		this.addSlotToContainer(new SlotTakeOnly(tedf, 19, 8, 72));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 20, 26, 72));

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

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();
			SlotCraftingOutput.checkAchievements(player, stack);

			if(index <= 20) {
				if(!this.mergeItemStack(stack, 21, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 0, 1, false)) return null;
				} else if(rStack.getItem() instanceof ItemMachineUpgrade ) {
					if(!this.mergeItemStack(stack, 1, 4, false)) return null;
				} else if(rStack.getItem() instanceof ItemChemistryTemplate) {
					if(!this.mergeItemStack(stack, 4, 5, false)) return null;
				} else { //proper shift-clicking filled/empty fluid tanks is an exercise in futility 
					if(!this.mergeItemStack(stack, 13, 19, false))
					if(!this.mergeItemStack(stack, 9, 11, false))
						return null;
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
		return nukeBoy.isUseableByPlayer(player);
	}
}
