package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineStardar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerStardar extends Container {
	
	TileEntityMachineStardar stardar;

	public ContainerStardar(InventoryPlayer player, TileEntityMachineStardar imGonnaThrowUpandShitAllovertheBeduggghhhh) {
		stardar = imGonnaThrowUpandShitAllovertheBeduggghhhh;
		
		//0 - battery
		//1-2 - upgrade slots
		
		//3-4 - fluid ID
		//5-10 - fluid I/O
		//11-13 - dissolved outputs
		
		//14 - crystal input
		//15 - niter input
		//16-17 - casting slots
		//18-23 - other material outputs
		
		this.addSlotToContainer(new Slot(imGonnaThrowUpandShitAllovertheBeduggghhhh, 0, 186, 109));	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return stardar.isUseableByPlayer(p_75145_1_);
	}

}
