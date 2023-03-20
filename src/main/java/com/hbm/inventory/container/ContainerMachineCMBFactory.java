package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotTakeOnly;
import com.hbm.tileentity.machine.TileEntityMachineCMBFactory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCMBFactory extends Container {
	
	private TileEntityMachineCMBFactory diFurnace;
	private int progress;
	
	public ContainerMachineCMBFactory(InventoryPlayer invPlayer, TileEntityMachineCMBFactory tedf) {
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 62 + 9, 17));
		this.addSlotToContainer(new Slot(tedf, 1, 80 + 9, 17));
		this.addSlotToContainer(new Slot(tedf, 2, 62 + 9, 53));
		this.addSlotToContainer(new Slot(tedf, 3, 80 + 9, 53));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 4, 134 + 9, 35));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 5, 62 - 9, 53));
		
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
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.process);
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
			else
			{
				if (!this.mergeItemStack(var5, 0, 4, false))
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
			
			if(this.progress != this.diFurnace.process)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.process);
			}
		}

		this.progress = this.diFurnace.process;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			diFurnace.process = j;
		}
	}
}
