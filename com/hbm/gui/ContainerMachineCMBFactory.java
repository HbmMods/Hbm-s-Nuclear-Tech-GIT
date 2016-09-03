package com.hbm.gui;

import com.hbm.tileentity.TileEntityMachineCMBFactory;
import com.hbm.tileentity.TileEntityMachineShredder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCMBFactory extends Container {
	
	private TileEntityMachineCMBFactory diFurnace;
	private int power;
	private int progress;
	private int waste;
	
	public ContainerMachineCMBFactory(InventoryPlayer invPlayer, TileEntityMachineCMBFactory tedf) {
		power = 0;
		waste = 0;
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 44, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 62, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 80, 18));
		this.addSlotToContainer(new Slot(tedf, 3, 44, 36));
		this.addSlotToContainer(new SlotDiFurnace(invPlayer.player, tedf, 4, 116, 18));
		
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
		crafting.sendProgressBarUpdate(this, 0, this.diFurnace.power);
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.process);
		crafting.sendProgressBarUpdate(this, 2, this.diFurnace.waste);
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
			
            if (par2 <= 29) {
				if (!this.mergeItemStack(var5, 30, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else
			{
				if (!this.mergeItemStack(var5, 0, 9, false))
					if (!this.mergeItemStack(var5, 27, 30, false))
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
			
			if(this.power != this.diFurnace.power)
			{
				par1.sendProgressBarUpdate(this, 0, this.diFurnace.power);
			}
			
			if(this.progress != this.diFurnace.process)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.process);
			}
			
			if(this.progress != this.diFurnace.waste)
			{
				par1.sendProgressBarUpdate(this, 2, this.diFurnace.waste);
			}
		}

		this.power = this.diFurnace.power;
		this.progress = this.diFurnace.process;
		this.waste = this.diFurnace.waste;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.power = j;
		}
		if(i == 1)
		{
			diFurnace.process = j;
		}
		if(i == 2)
		{
			diFurnace.waste = j;
		}
	}
}
