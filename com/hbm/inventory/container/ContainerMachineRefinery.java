package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.TileEntityMachineRefinery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineRefinery extends Container {

	private TileEntityMachineRefinery testNuke;
	private int power;
	private float oil;
	private int fuel;
	private int lubricant;
	private int diesel;
	private int kerosene;
	
	public ContainerMachineRefinery(InventoryPlayer invPlayer, TileEntityMachineRefinery tedf) {
		power = 0;
		oil = 0;
		fuel = 0;
		lubricant = 0;
		diesel = 0;
		kerosene = 0;
		
		testNuke = tedf;
		
		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 44, 54));
		//Canister Input
		this.addSlotToContainer(new Slot(tedf, 1, 134, 18));
		//Canister Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 2, 134, 54));
		//Fuel Input
		this.addSlotToContainer(new Slot(tedf, 3, 26, 72));
		//Fuel Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 4, 26, 108));
		//Lubricant Input
		this.addSlotToContainer(new Slot(tedf, 5, 62, 72));
		//Lubricant Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 6, 62, 108));
		//Diesel Input
		this.addSlotToContainer(new Slot(tedf, 7, 98, 72));
		//Diesel Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 8, 98, 108));
		//Kerosene Input
		this.addSlotToContainer(new Slot(tedf, 9, 134, 72));
		//Kerosene Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 10, 134, 108));
		//Sulfur Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 11, 152, 108));
		
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
		crafting.sendProgressBarUpdate(this, 0, this.testNuke.power);
		crafting.sendProgressBarUpdate(this, 1, this.testNuke.oil);
		crafting.sendProgressBarUpdate(this, 2, this.testNuke.fuel);
		crafting.sendProgressBarUpdate(this, 3, this.testNuke.lubricant);
		crafting.sendProgressBarUpdate(this, 4, this.testNuke.diesel);
		crafting.sendProgressBarUpdate(this, 5, this.testNuke.kerosene);
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
			
            if (par2 <= 11) {
				if (!this.mergeItemStack(var5, 12, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 1, false))
				if (!this.mergeItemStack(var5, 1, 2, false))
					if (!this.mergeItemStack(var5, 3, 4, false))
						if (!this.mergeItemStack(var5, 5, 6, false))
							if (!this.mergeItemStack(var5, 7, 8, false))
								if (!this.mergeItemStack(var5, 9, 10, false)) {
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
		return testNuke.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);

			if(this.power != this.testNuke.power)
			{
				par1.sendProgressBarUpdate(this, 0, this.testNuke.power);
			}
			if(this.oil != this.testNuke.oil)
			{
				par1.sendProgressBarUpdate(this, 1, this.testNuke.oil);
			}
			if(this.fuel != this.testNuke.fuel)
			{
				par1.sendProgressBarUpdate(this, 2, this.testNuke.fuel);
			}
			if(this.lubricant != this.testNuke.lubricant)
			{
				par1.sendProgressBarUpdate(this, 3, this.testNuke.lubricant);
			}
			if(this.diesel != this.testNuke.diesel)
			{
				par1.sendProgressBarUpdate(this, 4, this.testNuke.diesel);
			}
			if(this.kerosene != this.testNuke.kerosene)
			{
				par1.sendProgressBarUpdate(this, 5, this.testNuke.kerosene);
			}
		}

		this.power = this.testNuke.power;
		this.oil = this.testNuke.oil;
		this.oil = this.testNuke.fuel;
		this.oil = this.testNuke.lubricant;
		this.oil = this.testNuke.diesel;
		this.oil = this.testNuke.kerosene;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			testNuke.power = j;
		}
		if(i == 1)
		{
			testNuke.oil = j;
		}
		if(i == 2)
		{
			testNuke.fuel = j;
		}
		if(i == 3)
		{
			testNuke.lubricant = j;
		}
		if(i == 4)
		{
			testNuke.diesel = j;
		}
		if(i == 5)
		{
			testNuke.kerosene = j;
		}
	}
}
