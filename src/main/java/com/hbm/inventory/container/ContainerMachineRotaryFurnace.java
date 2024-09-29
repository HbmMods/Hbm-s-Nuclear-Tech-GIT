package com.hbm.inventory.container;

import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.TileEntityMachineRotaryFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerMachineRotaryFurnace extends Container {
	
	private TileEntityMachineRotaryFurnace furnace;

	public ContainerMachineRotaryFurnace(InventoryPlayer invPlayer, TileEntityMachineRotaryFurnace tile) {
		furnace = tile;
		
		//Inputs
		this.addSlotToContainer(new Slot(tile, 0, 8, 18));
		this.addSlotToContainer(new Slot(tile, 1, 26, 18));
		this.addSlotToContainer(new Slot(tile, 2, 44, 18));
		//Fluid ID
		this.addSlotToContainer(new Slot(tile, 3, 8, 54));
		//Solid fuel
		this.addSlotToContainer(new Slot(tile, 4, 44, 54));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 104 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 162));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();

			if(index <= 4) {
				if(!this.mergeItemStack(stack, 5, this.inventorySlots.size(), true)) return null;
			} else {
				if(TileEntityFurnace.isItemFuel(rStack)) {
					if(!this.mergeItemStack(stack, 4, 5, false)) return null;
				} else if(rStack.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(stack, 3, 4, false)) return null;
				} else {
					if(!this.mergeItemStack(stack, 0, 3, false)) return null;
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
		return furnace.isUseableByPlayer(player);
	}
}
