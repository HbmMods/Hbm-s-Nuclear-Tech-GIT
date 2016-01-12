package com.hbm.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hbm.blocks.TileEntityNukeFurnace;
import com.hbm.blocks.TileEntityRtgFurnace;

public class ContainerRtgFurnace extends Container {
	
	private TileEntityRtgFurnace diFurnace;
	private int dualCookTime;
	private int lastItemBurnTime;
	
	public ContainerRtgFurnace(InventoryPlayer invPlayer, TileEntityRtgFurnace tedf) {
		dualCookTime = 0;
		lastItemBurnTime = 0;
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 56, 17));
		this.addSlotToContainer(new Slot(tedf, 1, 38, 53));
		this.addSlotToContainer(new Slot(tedf, 2, 56, 53));
		this.addSlotToContainer(new Slot(tedf, 3, 74, 53));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 4, 116, 35));
		
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
	
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.diFurnace.dualCookTime);
	}
	
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUseableByPlayer(player);
	}
	
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);
			
			if(this.dualCookTime != this.diFurnace.dualCookTime)
			{
				par1.sendProgressBarUpdate(this, 0, this.diFurnace.dualCookTime);
			}
		}
		
		this.dualCookTime = this.diFurnace.dualCookTime;
	}
	
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.dualCookTime = j;
		}
	}
}
