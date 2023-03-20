package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.machine.ItemStamp;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerMachinePress extends Container {

	private TileEntityMachinePress press;

	public ContainerMachinePress(InventoryPlayer invPlayer, TileEntityMachinePress tedf) {
		press = tedf;

		// Coal
		this.addSlotToContainer(new Slot(tedf, 0, 26, 53));
		// Stamp
		this.addSlotToContainer(new Slot(tedf, 1, 80, 17));
		// Input
		this.addSlotToContainer(new Slot(tedf, 2, 80, 53));
		// Output
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 3, 140, 35));

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
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 3) {
				if(!this.mergeItemStack(var5, 4, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(TileEntityFurnace.isItemFuel(var3)) {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof ItemStamp) {
					if(!this.mergeItemStack(var5, 1, 2, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 2, 3, false)) {
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
