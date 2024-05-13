package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.TileEntityICFPress;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerICFPress extends Container {
	
	private TileEntityICFPress press;
	
	public ContainerICFPress(InventoryPlayer invPlayer, TileEntityICFPress tedf) {
		
		press = tedf;

		//Empty Capsule
		this.addSlotToContainer(new Slot(tedf, 0, 98, 18));
		//Filled Capsule
		this.addSlotToContainer(new SlotTakeOnly(tedf, 1, 98, 54));
		//Filled Muon
		this.addSlotToContainer(new Slot(tedf, 2, 8, 18));
		//Empty Muon
		this.addSlotToContainer(new SlotTakeOnly(tedf, 3, 8, 54));
		//Solid Fuels
		this.addSlotToContainer(new Slot(tedf, 4, 62, 54));
		this.addSlotToContainer(new Slot(tedf, 5, 134, 54));
		//Fluid IDs
		this.addSlotToContainer(new Slot(tedf, 6, 62, 18));
		this.addSlotToContainer(new Slot(tedf, 7, 134, 18));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 155));
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
				
				if(var3.getItem() == ModItems.icf_pellet_empty) {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(var5, 6, 8, false)) {
						return null;
					}
				} else if(var3.getItem() == ModItems.particle_muon) {
					if(!this.mergeItemStack(var5, 2, 3, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 4, 6, false)) {
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
		return press.isUseableByPlayer(player);
	}
}
