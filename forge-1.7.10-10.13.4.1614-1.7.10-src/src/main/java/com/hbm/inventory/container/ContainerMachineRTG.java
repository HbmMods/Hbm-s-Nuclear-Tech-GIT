package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineRTG;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineRTG extends Container {

	private TileEntityMachineRTG testNuke;
	private int heat;
	
	public ContainerMachineRTG(InventoryPlayer invPlayer, TileEntityMachineRTG tedf) {
		heat = 0;
		
		testNuke = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 26, 17));
		this.addSlotToContainer(new Slot(tedf, 1, 44, 17));
		this.addSlotToContainer(new Slot(tedf, 2, 62, 17));
		this.addSlotToContainer(new Slot(tedf, 3, 80, 17));
		this.addSlotToContainer(new Slot(tedf, 4, 98, 17));
		this.addSlotToContainer(new Slot(tedf, 5, 26, 35));
		this.addSlotToContainer(new Slot(tedf, 6, 44, 35));
		this.addSlotToContainer(new Slot(tedf, 7, 62, 35));
		this.addSlotToContainer(new Slot(tedf, 8, 80, 35));
		this.addSlotToContainer(new Slot(tedf, 9, 98, 35));
		this.addSlotToContainer(new Slot(tedf, 10, 26, 53));
		this.addSlotToContainer(new Slot(tedf, 11, 44, 53));
		this.addSlotToContainer(new Slot(tedf, 12, 62, 53));
		this.addSlotToContainer(new Slot(tedf, 13, 80, 53));
		this.addSlotToContainer(new Slot(tedf, 14, 98, 53));
		
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
		crafting.sendProgressBarUpdate(this, 0, this.testNuke.heat);
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

			if(this.heat != this.testNuke.heat)
			{
				par1.sendProgressBarUpdate(this, 0, this.testNuke.heat);
			}
		}

		this.heat = this.testNuke.heat;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			testNuke.heat = j;
		}
	}
}
