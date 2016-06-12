package com.hbm.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hbm.blocks.TileEntityMachineElectricFurnace;

public class ContainerElectricFurnace extends Container {
	
	private TileEntityMachineElectricFurnace diFurnace;
	private int dualCookTime;
	private int dualPower;
	private int lastItemBurnTime;
	
	public ContainerElectricFurnace(InventoryPlayer invPlayer, TileEntityMachineElectricFurnace tedf) {
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
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.power);
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
			
            if (par2 <= 2) {
				if (!this.mergeItemStack(var5, 3, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 1, 2, false))
			{
				if (!this.mergeItemStack(var5, 0, 1, false))
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
			
			if(this.dualCookTime != this.diFurnace.dualCookTime)
			{
				par1.sendProgressBarUpdate(this, 0, this.diFurnace.dualCookTime);
			}
			
			if(this.dualPower != this.diFurnace.power)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.power);
			}
		}
		
		this.dualCookTime = this.diFurnace.dualCookTime;
		this.dualPower = this.diFurnace.power;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.dualCookTime = j;
		}
		if(i == 1)
		{
			diFurnace.power = j;
		}
	}
}
