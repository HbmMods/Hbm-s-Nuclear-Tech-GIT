package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.tileentity.machine.TileEntityMachineMilkReformer;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineMilkReformer extends Container {

	private TileEntityMachineMilkReformer reformer;
	
	public ContainerMachineMilkReformer(InventoryPlayer invPlayer, TileEntityMachineMilkReformer tedf) {
		
		reformer = tedf;
		
		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 79, 8));
		//Canister Input
		this.addSlotToContainer(new Slot(tedf, 1, 45, 88));
		//Canister Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 2, 45, 106));
		//Reformate Input
		this.addSlotToContainer(new Slot(tedf, 3, 95, 88));
		//Reformate Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 4, 95, 106));
		//Gas Input
		this.addSlotToContainer(new Slot(tedf, 5, 122, 88));
		//Gas Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 6, 122, 106));
		//Hydrogen Input
		this.addSlotToContainer(new Slot(tedf, 7, 149, 88));
		//Hydrogen Oil Output
		this.addSlotToContainer(new SlotTakeOnly(tedf, 8, 149, 106));

		
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
					if(!this.mergeItemStack(var5, 1, 0, false)) {
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
		return reformer.isUseableByPlayer(player);
	}
}
