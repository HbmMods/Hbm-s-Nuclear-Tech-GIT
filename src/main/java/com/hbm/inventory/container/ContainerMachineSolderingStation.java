package com.hbm.inventory.container;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.inventory.recipes.SolderingRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineSolderingStation;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineSolderingStation extends Container {
	
	private TileEntityMachineSolderingStation solderer;

	public ContainerMachineSolderingStation(InventoryPlayer playerInv, TileEntityMachineSolderingStation tile) {
		solderer = tile;
		
		//Inputs
		for(int i = 0; i < 2; i++) for(int j = 0; j < 3; j++) this.addSlotToContainer(new SlotNonRetarded(tile, i * 3 + j, 17 + j * 18, 18 + i * 18));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 6, 107, 27));
		//Battery
		this.addSlotToContainer(new Slot(tile, 7, 152, 72));
		//Fluid ID
		this.addSlotToContainer(new Slot(tile, 8, 17, 63));
		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(tile, 9, 89, 63));
		this.addSlotToContainer(new SlotUpgrade(tile, 10, 107, 63));

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

			if(index <= 10) {
				if(!this.mergeItemStack(stack, 11, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 7, 8, false)) return null;
				} else if(rStack.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(stack, 8, 9, false)) return null;
				} else if(rStack.getItem() instanceof ItemMachineUpgrade ) {
					if(!this.mergeItemStack(stack, 9, 11, false)) return null;
				} else {
					for(AStack t : SolderingRecipes.toppings) if(t.matchesRecipe(stack, false)) if(!this.mergeItemStack(stack, 0, 3, false)) return null;
					for(AStack t : SolderingRecipes.pcb) if(t.matchesRecipe(stack, false)) if(!this.mergeItemStack(stack, 3, 5, false)) return null;
					for(AStack t : SolderingRecipes.solder) if(t.matchesRecipe(stack, false)) if(!this.mergeItemStack(stack, 5, 6, false)) return null;
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
		return solderer.isUseableByPlayer(player);
	}
}
