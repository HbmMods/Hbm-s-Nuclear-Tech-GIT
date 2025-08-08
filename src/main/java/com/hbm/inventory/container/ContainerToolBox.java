package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import static com.hbm.items.tool.ItemToolBox.*;

public class ContainerToolBox extends Container {

	private final InventoryToolBox box;

	public ContainerToolBox(InventoryPlayer invPlayer, InventoryToolBox box) {
		this.box = box;
		this.box.openInventory();

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 8; j++) {
				this.addSlotToContainer(new SlotNonRetarded(box, j + i * 8, 17 + j * 18, 49 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 129 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 187));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= box.getSizeInventory() - 1) {
				if(!InventoryUtil.mergeItemStack(this.inventorySlots, var5, box.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!InventoryUtil.mergeItemStack(this.inventorySlots, var5, 0, box.getSizeInventory(), false)) {
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack(null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		// prevents the player from moving around the currently open box
		if(mode == 2 && button == player.inventory.currentItem) return null;
		if(index == player.inventory.currentItem + 51) return null;
		return super.slotClick(index, button, mode, player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return box.isUseableByPlayer(player);
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.box.closeInventory();
	}
}
