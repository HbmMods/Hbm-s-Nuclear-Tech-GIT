package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCrystallizer extends Container {

	private TileEntityMachineCrystallizer diFurnace;

	public ContainerCrystallizer(InventoryPlayer invPlayer, TileEntityMachineCrystallizer tedf) {
		diFurnace = tedf;

		//Input
		this.addSlotToContainer(new Slot(tedf, 0, 62, 45));
		//Battery
		this.addSlotToContainer(new Slot(tedf, 1, 152, 72));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 2, 113, 45));
		//Fluid slots
		this.addSlotToContainer(new Slot(tedf, 3, 17, 18));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 4, 17, 54));
		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(tedf, 5, 80, 18));
		this.addSlotToContainer(new SlotUpgrade(tedf, 6, 98, 18));
		//Fluid ID
		this.addSlotToContainer(new Slot(tedf, 7, 35, 72));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 180));
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

			if(index <= 7) {
				if(!this.mergeItemStack(stack, 8, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 1, 2, false))
						return null;
					
				} else if(rStack.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(stack, 7, 8, false))
						return null;
					
				} else if(rStack.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(stack, 5, 7, false))
						return null;
					
				} else
					if(!this.mergeItemStack(stack, 0, 1, false))
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
