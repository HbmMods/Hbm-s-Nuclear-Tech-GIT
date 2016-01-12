package com.hbm.gui;

import com.hbm.blocks.TileEntityNukeGadget;
import com.hbm.blocks.TileEntityTestNuke;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerNukeGadget extends Container {

private TileEntityNukeGadget nukeGadget;
	
	public ContainerNukeGadget(InventoryPlayer invPlayer, TileEntityNukeGadget tedf) {
		
		nukeGadget = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 26, 35));
		this.addSlotToContainer(new Slot(tedf, 1, 8, 17));
		this.addSlotToContainer(new Slot(tedf, 2, 44, 17));
		this.addSlotToContainer(new Slot(tedf, 3, 8, 53));
		this.addSlotToContainer(new Slot(tedf, 4, 44, 53));
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
		return nukeGadget.isUseableByPlayer(player);
	}

}
