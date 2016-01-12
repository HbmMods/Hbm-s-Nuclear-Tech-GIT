package com.hbm.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hbm.blocks.TileEntityNukeTsar;

public class ContainerNukeTsar extends Container {

private TileEntityNukeTsar nukeTsar;
	
	public ContainerNukeTsar(InventoryPlayer invPlayer, TileEntityNukeTsar tedf) {
		
		nukeTsar = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 35, 17));
		this.addSlotToContainer(new Slot(tedf, 1, 71, 17));
		this.addSlotToContainer(new Slot(tedf, 2, 35, 53));
		this.addSlotToContainer(new Slot(tedf, 3, 71, 53));
		this.addSlotToContainer(new Slot(tedf, 4, 53, 35));
		this.addSlotToContainer(new Slot(tedf, 5, 98, 35));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}
	
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return nukeTsar.isUseableByPlayer(player);
	}
}
