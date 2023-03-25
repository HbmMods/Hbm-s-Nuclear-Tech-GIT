package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineRadar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerMachineRadar extends Container {
	
	public ContainerMachineRadar(InventoryPlayer invPlayer, TileEntityMachineRadar tedf) {
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
