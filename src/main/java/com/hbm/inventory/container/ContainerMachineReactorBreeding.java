package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.machine.ItemBreedingRod;
import com.hbm.tileentity.machine.TileEntityMachineReactorBreeding;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineReactorBreeding extends Container {

	private TileEntityMachineReactorBreeding reactor;

	public ContainerMachineReactorBreeding(InventoryPlayer invPlayer, TileEntityMachineReactorBreeding tedf) {

		reactor = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 35, 35));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 1, 125, 35));

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
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {

		ItemStack var3 = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {

			ItemStack stack = slot.getStack();
			var3 = stack.copy();

			if(index <= 1) {
				if(!this.mergeItemStack(stack, 1, this.inventorySlots.size(), true)) {
					return null;
				}

			} else if(stack.getItem() instanceof ItemBreedingRod) {
				if(!this.mergeItemStack(stack, 0, 1, false)) {
					return null;
				}
			} else {
				return null;
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);

			} else {
				slot.onSlotChanged();
			}
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return reactor.isUseableByPlayer(player);
	}
}
