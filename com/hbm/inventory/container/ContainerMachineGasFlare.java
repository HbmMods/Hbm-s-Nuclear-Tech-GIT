package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.TileEntityMachineGasFlare;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineGasFlare extends Container {

	private TileEntityMachineGasFlare testNuke;
	private int gas;
	private int power;
	
	public ContainerMachineGasFlare(InventoryPlayer invPlayer, TileEntityMachineGasFlare tedf) {
		gas = 0;
		power = 0;
		
		testNuke = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 44, 53));
		this.addSlotToContainer(new Slot(tedf, 1, 134, 17));
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
		crafting.sendProgressBarUpdate(this, 0, this.testNuke.gas);
		crafting.sendProgressBarUpdate(this, 1, this.testNuke.power);
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
			
            if (par2 <= 1) {
				if (!this.mergeItemStack(var5, 3, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 3, false))
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

			if(this.gas != this.testNuke.gas)
			{
				par1.sendProgressBarUpdate(this, 0, this.testNuke.gas);
			}
			if(this.power != this.testNuke.power)
			{
				par1.sendProgressBarUpdate(this, 1, this.testNuke.power);
			}
		}

		this.gas = this.testNuke.gas;
		this.power = this.testNuke.power;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			testNuke.gas = j;
		}
		if(i == 1)
		{
			testNuke.power = j;
		}
	}
}
