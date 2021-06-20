package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;

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

	public ContainerAnvil(InventoryPlayer inventory) {

		this.addSlotToContainer(new Slot(input, 0, 17, 27));
		this.addSlotToContainer(new Slot(input, 1, 53, 27));
		this.addSlotToContainer(new SlotMachineOutput(output, 0, 89, 27) {
			
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

			if(par2 <= 2) {
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
		}

		return var3;
	}
}
