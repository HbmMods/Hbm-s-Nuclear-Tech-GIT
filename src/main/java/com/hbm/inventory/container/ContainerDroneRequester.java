package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.tileentity.network.TileEntityDroneRequester;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDroneRequester extends ContainerCrateBase {

	public ContainerDroneRequester(InventoryPlayer invPlayer, TileEntityDroneRequester tedf) {
		super(invPlayer,tedf);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new SlotPattern(tedf, j + i * 3, 98 + j * 18, 17 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new Slot(tedf, j + i * 3 + 9, 26 + j * 18, 17 + i * 18));
			}
		}

		this.playerInv(invPlayer, 8, 103, 161);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 < 9) return null; //ignore filters

			if(par2 <= tile.getSizeInventory() - 1) {
				if(!this.mergeItemStack(var5, tile.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 9, tile.getSizeInventory(), false)) {
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {

		//L/R: 0
		//M3: 3
		//SHIFT: 1
		//DRAG: 5

		if(index < 0 || index > 8) {
			return super.slotClick(index, button, mode, player);
		}

		Slot slot = this.getSlot(index);

		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		TileEntityDroneRequester requester = (TileEntityDroneRequester) tile;

		if(slot.getHasStack())
			ret = slot.getStack().copy();

		if(button == 1 && mode == 0 && slot.getHasStack()) {
			requester.nextMode(index);
			return ret;

		} else {
			slot.putStack(held);
			requester.matcher.initPatternStandard(requester.getWorldObj(), slot.getStack(), index);

			return ret;
		}
	}
}
