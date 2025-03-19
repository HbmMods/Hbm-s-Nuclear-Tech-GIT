package com.hbm.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerCrateBase extends ContainerBase {

	//just there so prev stuff doesnt break
	protected IInventory crate = tile;

	public ContainerCrateBase(InventoryPlayer invPlayer, IInventory tedf) {
		super(invPlayer, tedf);
		tile.openInventory();
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		// prevents the player from moving around the currently open box
		if(mode == 2 && button == player.inventory.currentItem) return null;
		if(index == player.inventory.currentItem + 27 + this.tile.getSizeInventory()) return null;
		return super.slotClick(index, button, mode, player);
	}

	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		super.onContainerClosed(p_75134_1_);
		tile.closeInventory();
	}
}
