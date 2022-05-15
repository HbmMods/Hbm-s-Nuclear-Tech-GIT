package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineAssemfac;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerAssemfac extends Container {
	
	private TileEntityMachineAssemfac assemfac;

	public ContainerAssemfac(InventoryPlayer playerInv, TileEntityMachineAssemfac tile) {
		assemfac = tile;
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return false;
	}

}
