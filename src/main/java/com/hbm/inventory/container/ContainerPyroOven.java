package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.oil.TileEntityMachinePyroOven;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPyroOven extends Container {

	private TileEntityMachinePyroOven pyro;
	
	public ContainerPyroOven(InventoryPlayer invPlayer, TileEntityMachinePyroOven tedf) {
		pyro = tedf;

		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 152, 72));
		//Input
		this.addSlotToContainer(new Slot(tedf, 1, 35, 45));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 2, 89, 45));
		//Fluid ID
		this.addSlotToContainer(new Slot(tedf, 3, 8, 72));
		//Upgrades
		this.addSlotToContainer(new Slot(tedf, 4, 71, 72));
		this.addSlotToContainer(new Slot(tedf, 5, 89, 72));
		
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
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();

			if(index <= 5) {
				if(!this.mergeItemStack(stack, 6, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 0, 1, false)) return null;
				} else if(rStack.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(stack, 3, 4, false)) return null;
				} else if(rStack.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(stack, 4, 6, false)) return null;
				} else {
					if(!this.mergeItemStack(stack, 1, 2, false)) return null;
				}
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return rStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return pyro.isUseableByPlayer(player);
	}
}
