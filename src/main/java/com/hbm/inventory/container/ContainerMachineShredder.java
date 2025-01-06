package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBlades;
import com.hbm.tileentity.machine.TileEntityMachineShredder;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineShredder extends Container {

	private TileEntityMachineShredder diFurnace;
	private int progress;

	public ContainerMachineShredder(InventoryPlayer invPlayer, TileEntityMachineShredder tedf) {

		diFurnace = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 44, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 62, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 80, 18));
		this.addSlotToContainer(new Slot(tedf, 3, 44, 36));
		this.addSlotToContainer(new Slot(tedf, 4, 62, 36));
		this.addSlotToContainer(new Slot(tedf, 5, 80, 36));
		this.addSlotToContainer(new Slot(tedf, 6, 44, 54));
		this.addSlotToContainer(new Slot(tedf, 7, 62, 54));
		this.addSlotToContainer(new Slot(tedf, 8, 80, 54));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 9, 116, 18));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 10, 134, 18));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 11, 152, 18));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 12, 116, 36));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 13, 134, 36));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 14, 152, 36));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 15, 116, 54));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 16, 134, 54));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 17, 152, 54));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 18, 116, 72));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 19, 134, 72));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 20, 152, 72));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 21, 116, 90));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 22, 134, 90));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 23, 152, 90));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 24, 116, 108));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 25, 134, 108));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 26, 152, 108));
		this.addSlotToContainer(new Slot(tedf, 27, 44, 108));
		this.addSlotToContainer(new Slot(tedf, 28, 80, 108));
		this.addSlotToContainer(new Slot(tedf, 29, 8, 108));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 67));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 67));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.progress);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();

			if(index <= 29) {
				if(!this.mergeItemStack(stack, 30, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 29, 30, false)) return null;
				} else if(rStack.getItem() instanceof ItemBlades) {
					if(!this.mergeItemStack(stack, 27, 29, false)) return null;
				} else {
					if(!this.mergeItemStack(stack, 0, 9, false)) return null;
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
		return diFurnace.isUseableByPlayer(player);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for(int i = 0; i < this.crafters.size(); i++) {
			ICrafting par1 = (ICrafting) this.crafters.get(i);

			if(this.progress != this.diFurnace.progress) {
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.progress);
			}
		}

		this.progress = this.diFurnace.progress;
	}

	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1) {
			diFurnace.progress = j;
		}
	}
}
