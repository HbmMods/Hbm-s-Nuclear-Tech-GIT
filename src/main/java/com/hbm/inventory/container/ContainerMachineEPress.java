package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemStamp;
import com.hbm.tileentity.machine.TileEntityMachineEPress;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineEPress extends Container {

private TileEntityMachineEPress nukeBoy;

	public ContainerMachineEPress(InventoryPlayer invPlayer, TileEntityMachineEPress tedf) {
		
		nukeBoy = tedf;

		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 44, 53));
		//Stamp
		this.addSlotToContainer(new Slot(tedf, 1, 80, 17));
		//Input
		this.addSlotToContainer(new Slot(tedf, 2, 80, 53));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 3, 140, 35));
		//Upgrade
		this.addSlotToContainer(new SlotUpgrade(tedf, 4, 44, 21));
		
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

			if(par2 <= 4) {
				if(!this.mergeItemStack(var5, 5, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof IBatteryItem || var3.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(var5, 4, 5, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof ItemStamp) {
					if(!this.mergeItemStack(var5, 1, 2, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 2, 3, false)) {
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
		return nukeBoy.isUseableByPlayer(player);
	}
}
