package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityElectrolyser;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerElectrolyserFluid extends Container {

	private TileEntityElectrolyser electrolyser;

	public ContainerElectrolyserFluid(InventoryPlayer invPlayer, TileEntityElectrolyser tedf) {
		electrolyser = tedf;

		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 186, 109));
		//Upgrades
		this.addSlotToContainer(new Slot(tedf, 1, 186, 140));
		this.addSlotToContainer(new Slot(tedf, 2, 186, 158));
		//Fluid ID
		this.addSlotToContainer(new Slot(tedf, 3, 6, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 4, 6, 54));
		//Input
		this.addSlotToContainer(new Slot(tedf, 5, 24, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 6, 24, 54));
		//Output
		this.addSlotToContainer(new Slot(tedf, 7, 78, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 8, 78, 54));
		this.addSlotToContainer(new Slot(tedf, 9, 134, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 10, 134, 54));
		//Byproducts
		this.addSlotToContainer(new SlotTakeOnly(tedf, 11, 154, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 12, 154, 36));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 13, 154, 54));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 180));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 13) {
				if(!this.mergeItemStack(var5, 14, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof IBatteryItem || var3.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(var5, 1, 3, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(var5, 3, 4, false)) {
						return null;
					}
				} else {
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
		return electrolyser.isUseableByPlayer(player);
	}
}
