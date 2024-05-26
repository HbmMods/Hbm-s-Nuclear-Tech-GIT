package com.hbm.inventory.container;

import com.hbm.inventory.SlotSmelting;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnace;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineArcFurnace extends Container {

	private TileEntityMachineArcFurnace diFurnace;

	public ContainerMachineArcFurnace(InventoryPlayer invPlayer, TileEntityMachineArcFurnace tedf) {

		diFurnace = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 56, 17));
		this.addSlotToContainer(new SlotSmelting(invPlayer.player, tedf, 1, 116, 35));
		this.addSlotToContainer(new Slot(tedf, 2, 38, 53));
		this.addSlotToContainer(new Slot(tedf, 3, 56, 53));
		this.addSlotToContainer(new Slot(tedf, 4, 74, 53));
		this.addSlotToContainer(new Slot(tedf, 5, 8, 53));

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
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
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
				
				slot.onSlotChange(stack, rStack);
				
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 5, 6, false))
						return null;
					
				} else if(rStack.getItem() == ModItems.arc_electrode) {
					if(!this.mergeItemStack(stack, 2, 5, false))
						return null;
					
				} else if(!this.mergeItemStack(stack, 0, 1, false))
					return null;
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
		return diFurnace.isUseableByPlayer(player);
	}
}
