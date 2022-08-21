package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityFusionMultiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFusionMultiblock extends Container {
	
	private TileEntityFusionMultiblock diFurnace;
	
	private boolean isRunning;
	
	public ContainerFusionMultiblock(InventoryPlayer invPlayer, TileEntityFusionMultiblock tedf) {
		
		diFurnace = tedf;
		
		//Water Input
		this.addSlotToContainer(new Slot(tedf, 0, 8, 108 - 18));
		//Battery
		this.addSlotToContainer(new Slot(tedf, 1, 26, 108 - 18));
		//Deut Input
		this.addSlotToContainer(new Slot(tedf, 2, 134, 108 - 18));
		//Trit Input
		this.addSlotToContainer(new Slot(tedf, 3, 152, 108 - 18));
		//Startup
		this.addSlotToContainer(new Slot(tedf, 4, 53, 45));
		this.addSlotToContainer(new Slot(tedf, 5, 107, 45));
		this.addSlotToContainer(new Slot(tedf, 6, 53, 81));
		this.addSlotToContainer(new Slot(tedf, 7, 107, 81));
		this.addSlotToContainer(new Slot(tedf, 8, 80, 63));
		//Water Output
		this.addSlotToContainer(new Slot(tedf, 9, 8, 108));
		//Deut Output
		this.addSlotToContainer(new Slot(tedf, 10, 134, 108));
		//Trit Output
		this.addSlotToContainer(new Slot(tedf, 11, 152, 108));
		
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
		crafting.sendProgressBarUpdate(this, 1, isRunning ? 1 : 0);
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
			
			if(this.isRunning != this.diFurnace.isRunning())
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.isRunning() ? 1 : 0);
			}
		}
		
		this.isRunning = this.diFurnace.isRunning();
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			if(j == 0)
			{
				diFurnace.emptyPlasma();
			} else {
				diFurnace.fillPlasma();
			}
		}
	}
}
