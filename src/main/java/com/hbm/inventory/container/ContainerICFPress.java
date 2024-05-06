package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.tileentity.machine.TileEntityICFPress;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerICFPress extends Container {
	
	private TileEntityICFPress press;
	
	public ContainerICFPress(InventoryPlayer invPlayer, TileEntityICFPress tedf) {
		
		press = tedf;

		//Empty Capsule
		this.addSlotToContainer(new Slot(tedf, 0, 98, 18));
		//Filled Capsule
		this.addSlotToContainer(new SlotTakeOnly(tedf, 1, 98, 54));
		//Filled Muon
		this.addSlotToContainer(new Slot(tedf, 2, 8, 18));
		//Empty Muon
		this.addSlotToContainer(new SlotTakeOnly(tedf, 3, 8, 54));
		//Solid Fuels
		this.addSlotToContainer(new Slot(tedf, 4, 62, 54));
		this.addSlotToContainer(new Slot(tedf, 5, 134, 54));
		//Fluid IDs
		this.addSlotToContainer(new Slot(tedf, 6, 62, 18));
		this.addSlotToContainer(new Slot(tedf, 7, 134, 18));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 155));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return press.isUseableByPlayer(player);
	}
}
