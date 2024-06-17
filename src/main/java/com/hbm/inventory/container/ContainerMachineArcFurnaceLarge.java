package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.inventory.recipes.ArcFurnaceRecipes;
import com.hbm.inventory.recipes.ArcFurnaceRecipes.ArcFurnaceRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnaceLarge;
import com.hbm.util.InventoryUtil;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineArcFurnaceLarge extends Container {
	
	private TileEntityMachineArcFurnaceLarge furnace;

	public ContainerMachineArcFurnaceLarge(InventoryPlayer playerInv, TileEntityMachineArcFurnaceLarge tile) {
		furnace = tile;

		//Electrodes
		for(int i = 0; i < 3; i++) this.addSlotToContainer(new SlotNonRetarded(tile, i, 62 + i * 18, 22));
		//Battery
		this.addSlotToContainer(new Slot(tile, 3, 8, 108));
		//Upgrade
		this.addSlotToContainer(new Slot(tile, 4, 152, 108));
		//Inputs
		for(int i = 0; i < 4; i++) for(int j = 0; j < 5; j++) this.addSlotToContainer(new SlotArcFurnace(tile, 5 + j + i * 5, 44 + j * 18, 54 + i * 18));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 158 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 216));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();

			if(index <= 24) {
				if(!this.mergeItemStack(stack, 25, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, stack, 3, 4, false)) return null;
				} else if(rStack.getItem() == ModItems.arc_electrode) {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, stack, 4, 5, false)) return null;
				} else if(rStack.getItem() instanceof ItemMachineUpgrade) {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, stack, 0, 3, false)) return null;
				} else {
					if(!InventoryUtil.mergeItemStack(this.inventorySlots, stack, 5, 25, false)) return null;
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
		return furnace.isUseableByPlayer(player);
	}
	
	public static class SlotArcFurnace extends SlotNonRetarded {

		public SlotArcFurnace(IInventory inventory, int id, int x, int y) {
			super(inventory, id, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			TileEntityMachineArcFurnaceLarge furnace = (TileEntityMachineArcFurnaceLarge) this.inventory;
			if(furnace.liquidMode) return true;
			ArcFurnaceRecipe recipe = ArcFurnaceRecipes.getOutput(stack, furnace.liquidMode);
			if(recipe != null && recipe.solidOutput != null) {
				return recipe.solidOutput.stackSize * stack.stackSize <= recipe.solidOutput.getMaxStackSize() && stack.stackSize <= furnace.getMaxInputSize();
			}
			return false;
		}
		
		@Override
		public int getSlotStackLimit() {
			TileEntityMachineArcFurnaceLarge furnace = (TileEntityMachineArcFurnaceLarge) this.inventory;
			return this.getHasStack() ? furnace.getMaxInputSize() : 1;
		}
	}
}
