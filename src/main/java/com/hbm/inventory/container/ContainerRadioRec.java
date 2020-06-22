package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityRadioRec;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerRadioRec extends Container {
	
	public ContainerRadioRec(InventoryPlayer invPlayer, TileEntityRadioRec tedf) { }

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
}
