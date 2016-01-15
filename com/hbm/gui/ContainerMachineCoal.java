package com.hbm.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hbm.blocks.TileEntityMachineCoal;

public class ContainerMachineCoal extends Container {
	
	private TileEntityMachineCoal diFurnace;
	private int power;
	private int water;
	private int burnTime;
	
	public ContainerMachineCoal(InventoryPlayer invPlayer, TileEntityMachineCoal tedf) {
		power = 0;
		water = 0;
		burnTime = 0;
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 44, 53));
		this.addSlotToContainer(new Slot(tedf, 1, 80, 53));
		this.addSlotToContainer(new Slot(tedf, 2, 116, 53));
		
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
		crafting.sendProgressBarUpdate(this, 0, this.diFurnace.water);
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.power);
		crafting.sendProgressBarUpdate(this, 2, this.diFurnace.burnTime);
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
			
			if(this.water != this.diFurnace.water)
			{
				par1.sendProgressBarUpdate(this, 0, this.diFurnace.water);
			}
			
			if(this.power != this.diFurnace.power)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.power);
			}
			
			if(this.power != this.diFurnace.burnTime)
			{
				par1.sendProgressBarUpdate(this, 2, this.diFurnace.burnTime);
			}
		}
		
		this.water = this.diFurnace.water;
		this.power = this.diFurnace.power;
		this.burnTime = this.diFurnace.burnTime;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.water = j;
		}
		if(i == 1)
		{
			diFurnace.power = j;
		}
		if(i == 2)
		{
			diFurnace.burnTime = j;
		}
	}
}
