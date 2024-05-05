package com.hbm.inventory.container;

import com.hbm.inventory.recipes.LemegetonRecipes;
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

public class ContainerLemegeton extends Container {

	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 1, 1);
	public IInventory craftResult = new InventoryCraftResult();

	public ContainerLemegeton(InventoryPlayer inventory) {

		this.addSlotToContainer(new SlotCrafting(inventory.player, this.craftMatrix, this.craftResult, 0, 107, 35));
		this.addSlotToContainer(new Slot(this.craftMatrix, 0, 49, 35));

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
		this.craftResult.setInventorySlotContents(0, LemegetonRecipes.getRecipe(this.craftMatrix.getStackInSlot(0)));
	}

	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		if(!player.worldObj.isRemote) {
			ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(0);
			if(itemstack != null) player.dropPlayerItemWithRandomChoice(itemstack, false);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int slotNo) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotNo);

		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(slotNo <= 1) {
				if(!this.mergeItemStack(itemstack1, 2, this.inventorySlots.size(), true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if(!this.mergeItemStack(itemstack1, 1, 2, false)) {
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
		return player.inventory.hasItem(ModItems.book_lemegeton);
	}

	@Override
	public boolean func_94530_a(ItemStack stack, Slot slot) {
		return slot.inventory != this.craftResult && super.func_94530_a(stack, slot);
	}
}
