package com.hbm.gui.container;

import com.hbm.tileentity.TileEntityTestNuke;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTestNuke extends Container {

	private TileEntityTestNuke testNuke;
	
	public ContainerTestNuke(InventoryPlayer invPlayer, TileEntityTestNuke tedf) {
		
		testNuke = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 26, 36));
		this.addSlotToContainer(new Slot(tedf, 1, 44, 36));
		this.addSlotToContainer(new Slot(tedf, 2, 62, 36));
		this.addSlotToContainer(new Slot(tedf, 3, 80, 36));
		this.addSlotToContainer(new Slot(tedf, 4, 98, 36));
		
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
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return testNuke.isUseableByPlayer(player);
	}

}
