package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.tileentity.bomb.TileEntityLaunchPadBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLaunchPadLarge extends Container {
	
	private TileEntityLaunchPadBase launchpad;
	
	public ContainerLaunchPadLarge(InventoryPlayer invPlayer, TileEntityLaunchPadBase tedf) {
		
		launchpad = tedf;
		
		//Missile
		this.addSlotToContainer(new Slot(tedf, 0, 26, 36));
		//Designator
		this.addSlotToContainer(new Slot(tedf, 1, 26, 72));
		//Battery
		this.addSlotToContainer(new Slot(tedf, 2, 107, 90));
		//Fuel in
		this.addSlotToContainer(new Slot(tedf, 3, 125, 90));
		//Fuel out
		this.addSlotToContainer(new SlotTakeOnly(tedf, 4, 125, 108));
		//Oxidizer in
		this.addSlotToContainer(new Slot(tedf, 5, 143, 90));
		//Oxidizer out
		this.addSlotToContainer(new SlotTakeOnly(tedf, 6, 143, 108));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 154 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 212));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		return null; //TODO
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return launchpad.isUseableByPlayer(player);
	}
}
