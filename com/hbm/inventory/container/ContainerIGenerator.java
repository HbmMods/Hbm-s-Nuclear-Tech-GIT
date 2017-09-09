package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerIGenerator extends Container {
	
	private TileEntityMachineIGenerator diFurnace;

	private int power;
	private int torque;
	private int heat;
	private int water;
	private int lubricant;
	private int fuel;
	private int burn;
	
	public ContainerIGenerator(InventoryPlayer invPlayer, TileEntityMachineIGenerator tedf) {
		
		diFurnace = tedf;
		
		//Multi Purpose Slots
		this.addSlotToContainer(new Slot(tedf, 0, 8, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 26, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 44, 18));
		this.addSlotToContainer(new Slot(tedf, 3, 62, 18));
		this.addSlotToContainer(new Slot(tedf, 4, 80, 18));
		this.addSlotToContainer(new Slot(tedf, 5, 98, 18));
		this.addSlotToContainer(new Slot(tedf, 6, 8, 36));
		this.addSlotToContainer(new Slot(tedf, 7, 26, 36));
		this.addSlotToContainer(new Slot(tedf, 8, 44, 36));
		this.addSlotToContainer(new Slot(tedf, 9, 62, 36));
		this.addSlotToContainer(new Slot(tedf, 10, 80, 36));
		this.addSlotToContainer(new Slot(tedf, 11, 98, 36));
		//Solid Fuel Slot
		this.addSlotToContainer(new Slot(tedf, 12, 62, 108));
		//Fluid Slot
		this.addSlotToContainer(new Slot(tedf, 13, 98, 108));
		//Container Slot
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 14, 98, 72));
		//Battery Slot
		this.addSlotToContainer(new Slot(tedf, 15, 152, 108));
		
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
		crafting.sendProgressBarUpdate(this, 0, this.diFurnace.power);
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.torque);
		crafting.sendProgressBarUpdate(this, 2, this.diFurnace.heat);
		crafting.sendProgressBarUpdate(this, 3, this.diFurnace.water);
		crafting.sendProgressBarUpdate(this, 4, this.diFurnace.lubricant);
		crafting.sendProgressBarUpdate(this, 5, this.diFurnace.fuel);
		crafting.sendProgressBarUpdate(this, 6, this.diFurnace.burn);
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
			
            if (par2 <= 15) {
				if (!this.mergeItemStack(var5, 16, this.inventorySlots.size(), true))
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

			if(this.power != this.diFurnace.power)
			{
				par1.sendProgressBarUpdate(this, 0, this.diFurnace.power);
			}
			if(this.torque != this.diFurnace.torque)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.torque);
			}
			if(this.heat != this.diFurnace.heat)
			{
				par1.sendProgressBarUpdate(this, 2, this.diFurnace.heat);
			}
			if(this.water != this.diFurnace.water)
			{
				par1.sendProgressBarUpdate(this, 3, this.diFurnace.water);
			}
			if(this.lubricant != this.diFurnace.lubricant)
			{
				par1.sendProgressBarUpdate(this, 4, this.diFurnace.lubricant);
			}
			if(this.fuel != this.diFurnace.fuel)
			{
				par1.sendProgressBarUpdate(this, 5, this.diFurnace.fuel);
			}
			if(this.burn != this.diFurnace.burn)
			{
				par1.sendProgressBarUpdate(this, 6, this.diFurnace.burn);
			}
		}

		this.power = this.diFurnace.power;
		this.torque = this.diFurnace.torque;
		this.heat = this.diFurnace.heat;
		this.water = this.diFurnace.water;
		this.lubricant = this.diFurnace.lubricant;
		this.fuel = this.diFurnace.fuel;
		this.burn = this.diFurnace.burn;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.power = j;
		}
		if(i == 1)
		{
			diFurnace.torque = j;
		}
		if(i == 2)
		{
			diFurnace.heat = j;
		}
		if(i == 3)
		{
			diFurnace.water = j;
		}
		if(i == 4)
		{
			diFurnace.lubricant = j;
		}
		if(i == 5)
		{
			diFurnace.fuel = j;
		}
		if(i == 6)
		{
			diFurnace.burn = j;
		}
	}
}
