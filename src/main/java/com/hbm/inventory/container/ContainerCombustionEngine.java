package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.TileEntityMachineCombustionEngine;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCombustionEngine extends Container {

	private TileEntityMachineCombustionEngine engine;

	public ContainerCombustionEngine(InventoryPlayer invPlayer, TileEntityMachineCombustionEngine tedf) {
		this.engine = tedf;
		this.engine.openInventory();

		this.addSlotToContainer(new Slot(tedf, 0, 17, 17));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 1, 17, 53));
		this.addSlotToContainer(new Slot(tedf, 2, 88, 71));
		this.addSlotToContainer(new Slot(tedf, 3, 143, 71));
		this.addSlotToContainer(new Slot(tedf, 4, 35, 71));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 121 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 179));
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
				
				if(var3.getItem() instanceof IBatteryItem) {
					if(!this.mergeItemStack(var5, 3, 4, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(var5, 4, 5, false)) {
						return null;
					}
				} else if(var3.getItem() == ModItems.piston_set) {
					if(!this.mergeItemStack(var5, 2, 3, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
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
		return engine.isUseableByPlayer(player);
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.engine.closeInventory();
	}
}
