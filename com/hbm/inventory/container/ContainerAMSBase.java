package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityAMSBase;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAMSBase extends Container {

private TileEntityAMSBase amsBase;

	private int heat;
	private int field;
	private int efficiency;
	private int warning;
	private int mode;
	
	public ContainerAMSBase(InventoryPlayer invPlayer, TileEntityAMSBase tedf) {
		field = 0;
		efficiency = 0;
		amsBase = tedf;

		//Cool 1 In
		this.addSlotToContainer(new Slot(tedf, 0, 8, 18));
		//Cool 1 Out
		this.addSlotToContainer(new Slot(tedf, 1, 8, 54));
		//Cool 2 In
		this.addSlotToContainer(new Slot(tedf, 2, 152, 18));
		//Cool 2 Out
		this.addSlotToContainer(new Slot(tedf, 3, 152, 54));
		//Fuel 1 In
		this.addSlotToContainer(new Slot(tedf, 4, 8, 72));
		//Fuel 1 Out
		this.addSlotToContainer(new Slot(tedf, 5, 8, 108));
		//Fuel 2 In
		this.addSlotToContainer(new Slot(tedf, 6, 152, 72));
		//Fuel 2 Out
		this.addSlotToContainer(new Slot(tedf, 7, 152, 108));
		//Moderator
		this.addSlotToContainer(new Slot(tedf, 8, 80, 45));
		this.addSlotToContainer(new Slot(tedf, 9, 62, 63));
		this.addSlotToContainer(new Slot(tedf, 10, 98, 63));
		this.addSlotToContainer(new Slot(tedf, 11, 80, 81));
		//Core
		this.addSlotToContainer(new Slot(tedf, 12, 80, 63));
		
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
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 3) {
				if (!this.mergeItemStack(var5, 4, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else
				return null;
			
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
		return amsBase.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);
			
			if(this.heat != this.amsBase.heat)
			{
				par1.sendProgressBarUpdate(this, 0, this.amsBase.heat);
			}
			
			if(this.efficiency != this.amsBase.efficiency)
			{
				par1.sendProgressBarUpdate(this, 1, this.amsBase.efficiency);
			}
			
			if(this.warning != this.amsBase.warning)
			{
				par1.sendProgressBarUpdate(this, 2, this.amsBase.warning);
			}
			
			if(this.field != this.amsBase.field)
			{
				par1.sendProgressBarUpdate(this, 3, this.amsBase.field);
			}
			
			if(this.mode != this.amsBase.mode)
			{
				par1.sendProgressBarUpdate(this, 4, this.amsBase.mode);
			}
		}

		this.heat = this.amsBase.heat;
		this.field = this.amsBase.field;
		this.efficiency = this.amsBase.efficiency;
		this.warning = this.amsBase.warning;
		this.mode = this.amsBase.mode;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			amsBase.heat = j;
		}
		if(i == 1)
		{
			amsBase.efficiency = j;
		}
		if(i == 2)
		{
			amsBase.warning = j;
		}
		if(i == 3)
		{
			amsBase.field = j;
		}
		if(i == 4)
		{
			amsBase.mode = j;
		}
	}
}
