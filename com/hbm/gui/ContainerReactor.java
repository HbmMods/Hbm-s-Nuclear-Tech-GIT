package com.hbm.gui;

import com.hbm.blocks.TileEntityMachineReactor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerReactor extends Container {
	
	private TileEntityMachineReactor diFurnace;
	private int dualCookTime;
	private int dualPower;
	private int lastItemBurnTime;
	
	public ContainerReactor(InventoryPlayer invPlayer, TileEntityMachineReactor tedf) {
		dualCookTime = 0;
		dualPower = 0;
		lastItemBurnTime = 0;
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 56, 53));
		this.addSlotToContainer(new Slot(tedf, 1, 56, 17));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 2, 116, 35));
		
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
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.diFurnace.dualCookTime);
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.dualPower);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);
			
			if(this.dualCookTime != this.diFurnace.dualCookTime)
			{
				par1.sendProgressBarUpdate(this, 0, this.diFurnace.dualCookTime);
			}
			
			if(this.dualPower != this.diFurnace.dualPower)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.dualPower);
			}
		}
		
		this.dualCookTime = this.diFurnace.dualCookTime;
		this.dualPower = this.diFurnace.dualPower;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.dualCookTime = j;
		}
		if(i == 1)
		{
			diFurnace.dualPower = j;
		}
	}

}
