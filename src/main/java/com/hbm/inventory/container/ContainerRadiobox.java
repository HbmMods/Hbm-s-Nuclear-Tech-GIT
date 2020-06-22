package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityRadiobox;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerRadiobox extends Container {
	
	public ContainerRadiobox(InventoryPlayer invPlayer, TileEntityRadiobox tedf) { }

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
}
