package com.hbm.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCrateBase extends ContainerBase {

	//just there so prev stuff doesnt break
	protected IInventory crate = te;

	public ContainerCrateBase(InventoryPlayer invPlayer, IInventory tedf) {
		super(invPlayer, tedf);
		te.openInventory();
	}

	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		super.onContainerClosed(p_75134_1_);
		te.closeInventory();
	}
}
