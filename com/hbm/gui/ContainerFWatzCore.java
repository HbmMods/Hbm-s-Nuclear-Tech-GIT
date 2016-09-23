package com.hbm.gui;

import com.hbm.tileentity.TileEntityFWatzCore;
import com.hbm.tileentity.TileEntityFusionMultiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFWatzCore extends Container {
	
	private TileEntityFWatzCore diFurnace;
	
	private int cool;
	private int power;
	private int amat;
	private int aSchrab;
	private boolean isRunning;
	private int singularityType;
	
	public ContainerFWatzCore(InventoryPlayer invPlayer, TileEntityFWatzCore tedf) {
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 26, 108));
		this.addSlotToContainer(new Slot(tedf, 1, 62, 90));
		this.addSlotToContainer(new Slot(tedf, 2, 98, 90));
		this.addSlotToContainer(new Slot(tedf, 3, 134, 108));
		this.addSlotToContainer(new Slot(tedf, 4, 152, 108));
		
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
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.diFurnace.cool);
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.power);
		crafting.sendProgressBarUpdate(this, 2, this.diFurnace.amat);
		crafting.sendProgressBarUpdate(this, 3, this.diFurnace.aSchrab);
		crafting.sendProgressBarUpdate(this, 4, isRunning ? 1 : 0);
		crafting.sendProgressBarUpdate(this, 5, singularityType);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 4) {
				if (!this.mergeItemStack(var5, 5, this.inventorySlots.size(), true))
				{
					return null;
				}
			} else {
				return null;
			}
            
			if (var5.stackSize == 0)
			{
				var4.putStack((ItemStack) null);
			}
			else
			{
				var4.onSlotChanged();
			}
		}
		
		return var3;
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
			
			if(this.cool != this.diFurnace.cool)
			{
				par1.sendProgressBarUpdate(this, 0, this.diFurnace.cool);
			}
			
			if(this.power != this.diFurnace.power)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.power);
			}
			
			if(this.amat != this.diFurnace.amat)
			{
				par1.sendProgressBarUpdate(this, 2, this.diFurnace.amat);
			}
			
			if(this.aSchrab != this.diFurnace.aSchrab)
			{
				par1.sendProgressBarUpdate(this, 3, this.diFurnace.aSchrab);
			}
			
			if(this.isRunning != this.diFurnace.isRunning())
			{
				par1.sendProgressBarUpdate(this, 4, this.diFurnace.isRunning() ? 1 : 0);
			}
			
			if(this.singularityType != this.diFurnace.getSingularityType())
			{
				par1.sendProgressBarUpdate(this, 5, this.diFurnace.getSingularityType());
			}
		}
		
		this.cool = this.diFurnace.cool;
		this.power = this.diFurnace.power;
		this.amat = this.diFurnace.amat;
		this.aSchrab = this.diFurnace.aSchrab;
		this.isRunning = this.diFurnace.isRunning();
		this.singularityType = this.diFurnace.getSingularityType();
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.cool = j;
		}
		if(i == 1)
		{
			diFurnace.power = j;
		}
		if(i == 2)
		{
			diFurnace.amat = j;
		}
		if(i == 3)
		{
			diFurnace.aSchrab = j;
		}
		if(i == 4)
		{
			if(j == 0)
			{
				diFurnace.emptyPlasma();
			} else {
				diFurnace.fillPlasma();
			}
		}
	}
}
