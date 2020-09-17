package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachinePress extends Container {

private TileEntityMachinePress nukeBoy;

	private int power;
	private int progress;
	private int burnTime;
	private int maxBurn;
	
	public ContainerMachinePress(InventoryPlayer invPlayer, TileEntityMachinePress tedf) {

		power = 0;
		progress = 0;
		burnTime = 0;
		maxBurn = 0;
		
		nukeBoy = tedf;

		//Coal
		this.addSlotToContainer(new Slot(tedf, 0, 26, 53));
		//Stamp
		this.addSlotToContainer(new Slot(tedf, 1, 80, 17));
		//Input
		this.addSlotToContainer(new Slot(tedf, 2, 80, 53));
		//Output
		this.addSlotToContainer(new SlotMachineOutput(tedf, 3, 140, 35));
		
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
			else if (!this.mergeItemStack(var5, 2, 3, false))
				if (!this.mergeItemStack(var5, 0, 1, false))
					if (!this.mergeItemStack(var5, 1, 2, false))
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
		return nukeBoy.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);
			
			if(this.power != this.nukeBoy.power)
			{
				par1.sendProgressBarUpdate(this, 0, this.nukeBoy.power);
			}
			
			if(this.progress != this.nukeBoy.progress)
			{
				par1.sendProgressBarUpdate(this, 1, this.nukeBoy.progress);
			}
			
			if(this.burnTime != this.nukeBoy.burnTime)
			{
				par1.sendProgressBarUpdate(this, 2, this.nukeBoy.burnTime);
			}
			
			if(this.maxBurn != this.nukeBoy.maxBurn)
			{
				par1.sendProgressBarUpdate(this, 3, this.nukeBoy.maxBurn);
			}
		}

		this.power = this.nukeBoy.power;
		this.progress = this.nukeBoy.progress;
		this.burnTime = this.nukeBoy.burnTime;
		this.maxBurn = this.nukeBoy.maxBurn;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			nukeBoy.power = j;
		}
		if(i == 1)
		{
			nukeBoy.progress = j;
		}
		if(i == 2)
		{
			nukeBoy.burnTime = j;
		}
		if(i == 3)
		{
			nukeBoy.maxBurn = j;
		}
	}
}
