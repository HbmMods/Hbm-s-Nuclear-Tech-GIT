package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineRadGen;
import com.hbm.tileentity.machine.TileEntityMachineSiren;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineRadGen extends Container {
	
	private TileEntityMachineRadGen diFurnace;
	private int fuel;
	private int strength;
	private int mode;
	
	public ContainerMachineRadGen(InventoryPlayer invPlayer, TileEntityMachineRadGen tedf) {
		fuel = 0;
		strength = 0;
		mode = 0;
		
		diFurnace = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 17, 17));
		this.addSlotToContainer(new Slot(tedf, 1, 17, 53));
		this.addSlotToContainer(new Slot(tedf, 2, 125, 53));
		
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
			
            if (par2 <= 0) {
				if (!this.mergeItemStack(var5, 1, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 1, false))
			{
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
			
			if(this.fuel != this.diFurnace.fuel)
			{
				par1.sendProgressBarUpdate(this, 0, this.diFurnace.fuel);
			}
			
			if(this.strength != this.diFurnace.strength)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.strength);
			}
			
			if(this.mode != this.diFurnace.mode)
			{
				par1.sendProgressBarUpdate(this, 2, this.diFurnace.mode);
			}
		}
		
		this.fuel = this.diFurnace.fuel;
		this.strength = this.diFurnace.strength;
		this.mode = this.diFurnace.mode;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.fuel = j;
		}
		if(i == 1)
		{
			diFurnace.strength = j;
		}
		if(i == 2)
		{
			diFurnace.mode = j;
		}
	}
}
