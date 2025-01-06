package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.oil.TileEntityMachineGasFlare;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineGasFlare extends Container {

	private TileEntityMachineGasFlare testNuke;

	public ContainerMachineGasFlare(InventoryPlayer invPlayer, TileEntityMachineGasFlare tedf) {

		testNuke = tedf;

		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 143, 71));
		//Fluid in
		this.addSlotToContainer(new Slot(tedf, 1, 17, 17));
		//Fluid out
		this.addSlotToContainer(new SlotTakeOnly(tedf, 2, 17, 53));
		//Fluid ID
		this.addSlotToContainer(new Slot(tedf, 3, 35, 71));
		//Upgrades
		this.addSlotToContainer(new Slot(tedf, 4, 80, 71));
		this.addSlotToContainer(new Slot(tedf, 5, 98, 71));
		
		int offset = 37;

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + offset));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + offset));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 5) {
				if(!this.mergeItemStack(var5, 6, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(var5, 3, 4, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IBatteryItem) {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(var5, 4, 6, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 1, 2, false)) {
						return null;
					}
				}
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return testNuke.isUseableByPlayer(player);
	}
}
