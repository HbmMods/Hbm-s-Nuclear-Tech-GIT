package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineExposureChamber;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineExposureChamber extends Container {

	private TileEntityMachineExposureChamber chamber;

	public ContainerMachineExposureChamber(InventoryPlayer invPlayer, TileEntityMachineExposureChamber tedf) {
		this.chamber = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 8, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 2, 8, 54));
		this.addSlotToContainer(new Slot(tedf, 3, 80, 36));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 4, 116, 36));
		this.addSlotToContainer(new Slot(tedf, 5, 152, 54));
		this.addSlotToContainer(new Slot(tedf, 6, 44, 54));
		this.addSlotToContainer(new Slot(tedf, 7, 62, 54));

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
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 6) {
				if(!this.mergeItemStack(var5, 7, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(var5, 5, 7, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof IBatteryItem || var3.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(var5, 4, 5, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 0, 3, false)) {
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
		return chamber.isUseableByPlayer(player);
	}
}
