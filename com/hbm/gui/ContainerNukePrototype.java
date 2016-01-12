package com.hbm.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hbm.blocks.TileEntityNukeFleija;
import com.hbm.blocks.TileEntityNukePrototype;

public class ContainerNukePrototype extends Container {

private TileEntityNukePrototype nukeTsar;
	
	public ContainerNukePrototype(InventoryPlayer invPlayer, TileEntityNukePrototype tedf) {
		
		nukeTsar = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 8, 35));
		this.addSlotToContainer(new Slot(tedf, 1, 26, 35));
		this.addSlotToContainer(new Slot(tedf, 2, 44, 26));
		this.addSlotToContainer(new Slot(tedf, 3, 44, 44));
		this.addSlotToContainer(new Slot(tedf, 4, 62, 26));
		this.addSlotToContainer(new Slot(tedf, 5, 62, 44));
		this.addSlotToContainer(new Slot(tedf, 6, 80, 26));
		this.addSlotToContainer(new Slot(tedf, 7, 80, 44));
		this.addSlotToContainer(new Slot(tedf, 8, 98, 26));
		this.addSlotToContainer(new Slot(tedf, 9, 98, 44));
		this.addSlotToContainer(new Slot(tedf, 10, 116, 26));
		this.addSlotToContainer(new Slot(tedf, 11, 116, 44));
		this.addSlotToContainer(new Slot(tedf, 12, 134, 35));
		this.addSlotToContainer(new Slot(tedf, 13, 152, 35));
		
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
