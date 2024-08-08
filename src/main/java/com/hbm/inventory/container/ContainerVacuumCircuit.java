package com.hbm.inventory.container;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.inventory.recipes.VacuumCircuitRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineVacuumCircuit;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerVacuumCircuit extends Container {

	private TileEntityMachineVacuumCircuit sucker;

	public ContainerVacuumCircuit(InventoryPlayer playerInv, TileEntityMachineVacuumCircuit tile) {
		sucker = tile;
		
		//Inputs
		for(int i = 0; i < 2; i++) for(int j = 0; j < 2; j++) this.addSlotToContainer(new SlotNonRetarded(tile, i * 2 + j, 11 + j * 18, 39 + i * 18));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 4, 85, 48));
		//Battery
		this.addSlotToContainer(new Slot(tile, 5, 132, 72));

		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(tile, 6, 10, 13));
		this.addSlotToContainer(new SlotUpgrade(tile, 7, 28, 13));

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
					if(!this.mergeItemStack(stack, 5, 6, false)) return null;
				} else if(rStack.getItem() instanceof ItemMachineUpgrade ) {
					if(!this.mergeItemStack(stack, 6, 8, false)) return null;
				} else {
					for(AStack t : VacuumCircuitRecipes.wafer) if(t.matchesRecipe(stack, false)) if(!this.mergeItemStack(stack, 0, 2, false)) return null;
					for(AStack t : VacuumCircuitRecipes.pcb) if(t.matchesRecipe(stack, false)) if(!this.mergeItemStack(stack, 2, 4, false)) return null;
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
		return sucker.isUseableByPlayer(player);
	}

}
