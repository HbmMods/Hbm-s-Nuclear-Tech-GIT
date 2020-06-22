package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGenerator extends Container {
	
	private TileEntityMachineGenerator diFurnace;
	
	private int heat;
	
	public ContainerGenerator(InventoryPlayer invPlayer, TileEntityMachineGenerator tedf) {
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 116, 36));
		this.addSlotToContainer(new Slot(tedf, 1, 134, 36));
		this.addSlotToContainer(new Slot(tedf, 2, 152, 36));
		this.addSlotToContainer(new Slot(tedf, 3, 116, 54));
		this.addSlotToContainer(new Slot(tedf, 4, 134, 54));
		this.addSlotToContainer(new Slot(tedf, 5, 152, 54));
		this.addSlotToContainer(new Slot(tedf, 6, 116, 72));
		this.addSlotToContainer(new Slot(tedf, 7, 134, 72));
		this.addSlotToContainer(new Slot(tedf, 8, 152, 72));
		this.addSlotToContainer(new Slot(tedf, 9, 8, 90));
		this.addSlotToContainer(new Slot(tedf, 10, 26, 90));
		this.addSlotToContainer(new Slot(tedf, 11, 62, 90));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 12, 8, 90 + 18));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 13, 26, 90 + 18));
		
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
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.heat);
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
			else if (!this.mergeItemStack(var5, 0, 12, false))
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
			
			if(this.heat != this.diFurnace.heat)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.heat);
			}
		}
		
		this.heat = this.diFurnace.heat;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			diFurnace.heat = j;
		}
	}
}
