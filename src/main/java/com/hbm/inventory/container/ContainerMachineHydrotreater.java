package com.hbm.inventory.container;

import com.hbm.inventory.SlotDeprecated;
import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.oil.TileEntityMachineHydrotreater;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineHydrotreater extends Container {

	private TileEntityMachineHydrotreater hydrotreater;
	
	public ContainerMachineHydrotreater(InventoryPlayer invPlayer, TileEntityMachineHydrotreater tedf) {
		
		hydrotreater = tedf;
		
		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 17, 90));
		//Canister Input
		this.addSlotToContainer(new Slot(tedf, 1, 35, 90));
		//Canister Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 2, 35, 108));
		//Hydrogen Input (removed, requires pressurization)
		this.addSlotToContainer(new SlotDeprecated(tedf, 3, 53, 90));
		//Hydrogen Output (samesies)
		this.addSlotToContainer(new SlotDeprecated(tedf, 4, 53, 108));
		//Desulfated Oil Input
		this.addSlotToContainer(new Slot(tedf, 5, 125, 90));
		//Desulfated Oil Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 6, 125, 108));
		//Sour Gas Input
		this.addSlotToContainer(new Slot(tedf, 7, 143, 90));
		//Sour Gas Oil Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 8, 143, 108));
		//Fluid ID
		this.addSlotToContainer(new Slot(tedf, 9, 17, 108));
		//Catalyst
		this.addSlotToContainer(new Slot(tedf, 10, 89, 36));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 156 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 214));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 10) {
				if(!this.mergeItemStack(var5, 11, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof IBatteryItem) {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(var5, 9, 10, false)) {
						return null;
					}
				} else if(var3.getItem() == ModItems.catalytic_converter) {
					if(!this.mergeItemStack(var5, 10, 11, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 1, 2, false))
						if(!this.mergeItemStack(var5, 3, 4, false))
							if(!this.mergeItemStack(var5, 5, 6, false))
								if(!this.mergeItemStack(var5, 7, 8, false))
									return null;
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
		return hydrotreater.isUseableByPlayer(player);
	}
}
