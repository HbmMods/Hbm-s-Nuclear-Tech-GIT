package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.recipes.anvil.AnvilRecipes;
import com.hbm.inventory.recipes.anvil.AnvilSmithingRecipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAnvil extends Container {
	
	public InventoryBasic input = new InventoryBasic("Input", false, 8);
	public IInventory output = new InventoryCraftResult();
	
	public int tier; //because we can't trust these rascals with their packets
	
	public ContainerAnvil(InventoryPlayer inventory, int tier) {
		this.tier = tier;
		
		this.addSlotToContainer(new SmithingSlot(input, 0, 17, 27));
		this.addSlotToContainer(new SmithingSlot(input, 1, 53, 27));
		this.addSlotToContainer(new SlotCraftingOutput(inventory.player, output, 0, 89, 27) {
			
			@Override
			public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
				super.onPickupFromSlot(player, stack);
				
				ItemStack left = ContainerAnvil.this.input.getStackInSlot(0);
				ItemStack right = ContainerAnvil.this.input.getStackInSlot(1);
				
				if(left == null || right == null) {
					return;
				}
				
				for(com.hbm.inventory.recipes.anvil.AnvilSmithingRecipe rec : AnvilRecipes.getSmithing()) {
					
					int i = rec.matchesInt(left, right);
					
					if(i != -1) {
						ContainerAnvil.this.input.decrStackSize(0, rec.amountConsumed(0, i == 1));
						ContainerAnvil.this.input.decrStackSize(1, rec.amountConsumed(1, i == 1));
						ContainerAnvil.this.updateSmithing();
						return;
					}
				}
			}
			
		});
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142 + 56));
		}
		
		this.onCraftMatrixChanged(this.input);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
			if(par2 == 2) {
				
				if(!this.mergeItemStack(var5, 3, this.inventorySlots.size(), true)) {
					return null;
				}
				var4.onSlotChange(var5, var3);
				
			} else if(par2 <= 1) {
				if(!this.mergeItemStack(var5, 3, this.inventorySlots.size(), true)) {
					return null;
				} else {
					var4.onPickupFromSlot(p_82846_1_, var5);
				}
			} else {
				
				if(!this.mergeItemStack(var5, 0, 2, false))
					return null;
			}
			
			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
			
			var4.onPickupFromSlot(p_82846_1_, var5);
		}
		
		return var3;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		
		if(!player.worldObj.isRemote) {
			for(int i = 0; i < this.input.getSizeInventory(); ++i) {
				ItemStack itemstack = this.input.getStackInSlotOnClosing(i);
				
				if(itemstack != null) {
					player.dropPlayerItemWithRandomChoice(itemstack, false);
				}
			}
		}
	}
	
	public class SmithingSlot extends Slot {
		
		public SmithingSlot(IInventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}
		
		public void onSlotChanged() {
			super.onSlotChanged();
			ContainerAnvil.this.updateSmithing();
		}
		
		@Override
		public void putStack(ItemStack stack) {
			super.putStack(stack);
		}
		
		public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
			super.onPickupFromSlot(player, stack);
			ContainerAnvil.this.updateSmithing();
		}
	}
	
	private void updateSmithing() {
		
		ItemStack left = this.input.getStackInSlot(0);
		ItemStack right = this.input.getStackInSlot(1);
		
		if(left == null || right == null) {
			this.output.setInventorySlotContents(0, null);
			return;
		}
		
		for(AnvilSmithingRecipe rec : AnvilRecipes.getSmithing()) {
			
			if(rec.matches(left, right) && rec.tier <= this.tier) {
				this.output.setInventorySlotContents(0, rec.getOutput(left, right));
				return;
			}
		}
		
		this.output.setInventorySlotContents(0, null);
	}
}
