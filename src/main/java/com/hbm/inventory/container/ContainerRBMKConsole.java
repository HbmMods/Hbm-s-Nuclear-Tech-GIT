package com.hbm.inventory.container;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRBMKConsole extends Container {

	private TileEntityRBMKConsole rbmk;

	public ContainerRBMKConsole(InventoryPlayer invPlayer, TileEntityRBMKConsole tedf) {
		this.rbmk = tedf;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			return null;
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return rbmk.isUseableByPlayer(player);
	}
}
