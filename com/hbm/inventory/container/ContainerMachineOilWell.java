package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.TileEntityMachineOilWell;
import com.hbm.tileentity.TileEntityMachineRTG;
import com.hbm.tileentity.TileEntityMachineUF6Tank;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineOilWell extends Container {

	private TileEntityMachineOilWell testNuke;
	private int oil;
	private int power;
	private int warning;
	
	public ContainerMachineOilWell(InventoryPlayer invPlayer, TileEntityMachineOilWell tedf) {
		oil = 0;
		power = 0;
		warning = 0;
		
		testNuke = tedf;
		
		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 44, 53));
		//Canister Input
		this.addSlotToContainer(new Slot(tedf, 1, 134, 17));
		//Canister Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 2, 134, 53));
		
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
		crafting.sendProgressBarUpdate(this, 0, this.testNuke.oil);
		crafting.sendProgressBarUpdate(this, 1, this.testNuke.power);
		crafting.sendProgressBarUpdate(this, 2, this.testNuke.warning);
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
			
            if (par2 <= 14) {
				if (!this.mergeItemStack(var5, 15, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 15, false))
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
		return testNuke.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);

			if(this.oil != this.testNuke.oil)
			{
				par1.sendProgressBarUpdate(this, 0, this.testNuke.oil);
			}
			if(this.power != this.testNuke.power)
			{
				par1.sendProgressBarUpdate(this, 1, this.testNuke.power);
			}
			if(this.power != this.testNuke.warning)
			{
				par1.sendProgressBarUpdate(this, 2, this.testNuke.warning);
			}
		}

		this.oil = this.testNuke.oil;
		this.power = this.testNuke.power;
		this.warning = this.testNuke.warning;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			testNuke.oil = j;
		}
		if(i == 1)
		{
			testNuke.power = j;
		}
		if(i == 2)
		{
			testNuke.warning = j;
		}
	}
}
