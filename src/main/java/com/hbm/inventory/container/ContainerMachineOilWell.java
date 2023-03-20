package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.oil.TileEntityOilDrillBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineOilWell extends Container {

	private TileEntityOilDrillBase well;

	public ContainerMachineOilWell(InventoryPlayer invPlayer, TileEntityOilDrillBase tedf) {

		well = tedf;

		// Battery
		this.addSlotToContainer(new Slot(tedf, 0, 8, 53));
		// Canister Input
		this.addSlotToContainer(new Slot(tedf, 1, 80, 17));
		// Canister Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 2, 80, 53));
		// Gas Input
		this.addSlotToContainer(new Slot(tedf, 3, 125, 17));
		// Gas Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 4, 125, 53));
		//Upgrades
		this.addSlotToContainer(new Slot(tedf, 5, 152, 17));
		this.addSlotToContainer(new Slot(tedf, 6, 152, 35));
		this.addSlotToContainer(new Slot(tedf, 7, 152, 53));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 7) {
				if(!this.mergeItemStack(var5, 8, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var5.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(var5, 5, 8, true)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 0, 2, false)) {
						if(!this.mergeItemStack(var5, 3, 4, false)) {
							return null;
						}
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
		return well.isUseableByPlayer(player);
	}
}
