package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.TileEntityMachineOilWell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineOilWell extends Container {

	private TileEntityMachineOilWell testNuke;
	private int power;
	private int warning;
	private int warning2;
	
	public ContainerMachineOilWell(InventoryPlayer invPlayer, TileEntityMachineOilWell tedf) {
		power = 0;
		warning = 0;
		warning2 = 0;
		
		testNuke = tedf;
		
		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 44, 54));
		//Canister Input
		this.addSlotToContainer(new Slot(tedf, 1, 134, 18));
		//Canister Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 2, 134, 54));
		//Gas Input
		this.addSlotToContainer(new Slot(tedf, 3, 134, 72));
		//Gas Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 4, 134, 108));
		//Chip
		this.addSlotToContainer(new Slot(tedf, 5, 8, 90));
		
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
		crafting.sendProgressBarUpdate(this, 1, this.testNuke.warning);
		crafting.sendProgressBarUpdate(this, 2, this.testNuke.warning2);
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
			
            if (par2 <= 5) {
				if (!this.mergeItemStack(var5, 6, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 2, false))
			{
				if (!this.mergeItemStack(var5, 3, 4, false))
					if (!this.mergeItemStack(var5, 5, 6, false))
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
			if(this.warning != this.testNuke.warning)
			{
				par1.sendProgressBarUpdate(this, 1, this.testNuke.warning);
			}
			if(this.warning2 != this.testNuke.warning2)
			{
				par1.sendProgressBarUpdate(this, 2, this.testNuke.warning2);
			}
		}

		this.power = this.testNuke.power;
		this.warning = this.testNuke.warning;
		this.warning2 = this.testNuke.warning2;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			testNuke.power = j;
		}
		if(i == 1)
		{
			testNuke.warning = j;
		}
		if(i == 2)
		{
			testNuke.warning2 = j;
		}
	}
}
