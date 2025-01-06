package com.hbm.inventory.container;

import com.hbm.inventory.recipes.MagicRecipes;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class ContainerBook extends Container {

	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
	public IInventory craftResult = new InventoryCraftResult();

	public ContainerBook(InventoryPlayer inventory) {

		this.addSlotToContainer(new SlotCrafting(inventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));

		for(int l = 0; l < 2; ++l) {
			for(int i1 = 0; i1 < 2; ++i1) {
				this.addSlotToContainer(new Slot(this.craftMatrix, i1 + l * 2, 30 + i1 * 36, 17 + l * 36));
			}
		}

		for(int l = 0; l < 3; ++l) {
			for(int i1 = 0; i1 < 9; ++i1) {
				this.addSlotToContainer(new Slot(inventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
			}
		}

		for(int l = 0; l < 9; ++l) {
			this.addSlotToContainer(new Slot(inventory, l, 8 + l * 18, 142));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	public void onCraftMatrixChanged(IInventory inventory) {
		this.craftResult.setInventorySlotContents(0, MagicRecipes.getRecipe(this.craftMatrix));
	}

	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		if(!player.worldObj.isRemote) {

			for(int i = 0; i < 4; ++i) {
				ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

				if(itemstack != null)
					player.dropPlayerItemWithRandomChoice(itemstack, false);
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(p_82846_2_);

		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(p_82846_2_ == 0) {
				if(!this.mergeItemStack(itemstack1, 10 - 5, 46 - 5, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if(p_82846_2_ >= 10 - 5 && p_82846_2_ < 37 - 5) {
				if(!this.mergeItemStack(itemstack1, 37 - 5, 46 - 5, false)) {
					return null;
				}
			} else if(p_82846_2_ >= 37 - 5 && p_82846_2_ < 46 - 5) {
				if(!this.mergeItemStack(itemstack1, 10 - 5, 37 - 5, false)) {
					return null;
				}
			} else if(!this.mergeItemStack(itemstack1, 10 - 5, 46 - 5, false)) {
				return null;
			}

			if(itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if(itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(p_82846_1_, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.inventory.hasItem(ModItems.book_of_);
	}

	@Override
	public boolean func_94530_a(ItemStack stack, Slot slot) {
		return slot.inventory != this.craftResult && super.func_94530_a(stack, slot);
	}
}
