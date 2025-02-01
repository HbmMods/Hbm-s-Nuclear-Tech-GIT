package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.recipes.AmmoPressRecipes;
import com.hbm.inventory.recipes.AmmoPressRecipes.AmmoPressRecipe;
import com.hbm.tileentity.machine.TileEntityMachineAmmoPress;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineAmmoPress extends Container {
	
	private TileEntityMachineAmmoPress press;

	public ContainerMachineAmmoPress(InventoryPlayer playerInv, TileEntityMachineAmmoPress tile) {
		press = tile;
		
		//Inputs
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new Slot(tile, i * 3 + j, 116 + j * 18, 18 + i * 18));
			}
		}
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 9, 134, 72));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 118 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 176));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();

			if(index <= 9) {
				if(!this.mergeItemStack(stack, 10, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(press.selectedRecipe < 0 || press.selectedRecipe >= AmmoPressRecipes.recipes.size()) return null;
				AmmoPressRecipe recipe = AmmoPressRecipes.recipes.get(press.selectedRecipe);
				
				for(int i = 0; i < 9; i++) {
					if(recipe.input[i] == null) continue;
					if(recipe.input[i].matchesRecipe(stack, true)) {
						if(!this.mergeItemStack(stack, i, i + 1, false)) {
							return null;
						}
					}
				}
				
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
		return press.isUseableByPlayer(player);
	}
}
