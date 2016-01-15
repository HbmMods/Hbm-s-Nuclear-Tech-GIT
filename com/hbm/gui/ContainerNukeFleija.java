package com.hbm.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hbm.blocks.TileEntityNukeFleija;

public class ContainerNukeFleija extends Container {

private TileEntityNukeFleija nukeTsar;
	
	public ContainerNukeFleija(InventoryPlayer invPlayer, TileEntityNukeFleija tedf) {
		
		nukeTsar = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 8, 36));
		this.addSlotToContainer(new Slot(tedf, 1, 152, 36));
		this.addSlotToContainer(new Slot(tedf, 2, 44, 18));
		this.addSlotToContainer(new Slot(tedf, 3, 44, 36));
		this.addSlotToContainer(new Slot(tedf, 4, 44, 54));
		this.addSlotToContainer(new Slot(tedf, 5, 80, 18));
		this.addSlotToContainer(new Slot(tedf, 6, 98, 18));
		this.addSlotToContainer(new Slot(tedf, 7, 80, 36));
		this.addSlotToContainer(new Slot(tedf, 8, 98, 36));
		this.addSlotToContainer(new Slot(tedf, 9, 80, 54));
		this.addSlotToContainer(new Slot(tedf, 10, 98, 54));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return nukeTsar.isUseableByPlayer(player);
	}
}
