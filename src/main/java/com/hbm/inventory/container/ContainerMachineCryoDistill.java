package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.TileEntityMachineCryoDistill;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCryoDistill extends Container {

	private TileEntityMachineCryoDistill cryo;
	
	public ContainerMachineCryoDistill(InventoryPlayer invPlayer, TileEntityMachineCryoDistill tedf) {
		
		cryo = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 145,  71));
		this.addSlotToContainer(new Slot(tedf, 1, 57,  71));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 2, 57,  89));
		this.addSlotToContainer(new Slot(tedf, 3, 79,  71));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 4,  79,  89));
		this.addSlotToContainer(new Slot(tedf, 5,  101, 71));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 6, 101,  89));
		this.addSlotToContainer(new Slot(tedf, 7, 19, 71));
		this.addSlotToContainer(new Slot(tedf, 8, 123,  71));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 9, 123, 89));
		int offset = 6;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 156 + i * 18 - offset));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 214 - offset));
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

			if(par2 <= 9) {
				if(!this.mergeItemStack(var5, 8, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof IBatteryItem) {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(var5, 11, 12, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 1, 2, false))
						if(!this.mergeItemStack(var5, 3, 4, false))
							if(!this.mergeItemStack(var5, 5, 6, false))
								if(!this.mergeItemStack(var5, 7, 8, false))
									if(!this.mergeItemStack(var5, 9, 10, false))
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
		return cryo.isUseableByPlayer(player);
	}
}
