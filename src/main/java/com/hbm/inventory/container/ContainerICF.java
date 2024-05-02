package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityICF;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerICF extends Container {
	
	protected TileEntityICF icf;
	
	public ContainerICF(InventoryPlayer invPlayer, TileEntityICF tedf) {
		this.icf = tedf;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 147 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 205));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		return null; //TODO
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return icf.isUseableByPlayer(player);
	}
}
