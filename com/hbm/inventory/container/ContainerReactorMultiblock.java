package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityReactorMultiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerReactorMultiblock extends Container {
	
	private TileEntityReactorMultiblock diFurnace;
	
	private int heat;
	
	public ContainerReactorMultiblock(InventoryPlayer invPlayer, TileEntityReactorMultiblock tedf) {
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 62, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 80, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 98, 18));
		this.addSlotToContainer(new Slot(tedf, 3, 116, 18));
		this.addSlotToContainer(new Slot(tedf, 4, 134, 18));
		this.addSlotToContainer(new Slot(tedf, 5, 152, 18));
		this.addSlotToContainer(new Slot(tedf, 6, 62, 36));
		this.addSlotToContainer(new Slot(tedf, 7, 80, 36));
		this.addSlotToContainer(new Slot(tedf, 8, 98, 36));
		this.addSlotToContainer(new Slot(tedf, 9, 116, 36));
		this.addSlotToContainer(new Slot(tedf, 10, 134, 36));
		this.addSlotToContainer(new Slot(tedf, 11, 152, 36));
		this.addSlotToContainer(new Slot(tedf, 12, 62, 54));
		this.addSlotToContainer(new Slot(tedf, 13, 80, 54));
		this.addSlotToContainer(new Slot(tedf, 14, 98, 54));
		this.addSlotToContainer(new Slot(tedf, 15, 116, 54));
		this.addSlotToContainer(new Slot(tedf, 16, 134, 54));
		this.addSlotToContainer(new Slot(tedf, 17, 152, 54));
		this.addSlotToContainer(new Slot(tedf, 18, 62, 72));
		this.addSlotToContainer(new Slot(tedf, 19, 80, 72));
		this.addSlotToContainer(new Slot(tedf, 20, 98, 72));
		this.addSlotToContainer(new Slot(tedf, 21, 116, 72));
		this.addSlotToContainer(new Slot(tedf, 22, 134, 72));
		this.addSlotToContainer(new Slot(tedf, 23, 152, 72));
		this.addSlotToContainer(new Slot(tedf, 24, 62, 90));
		this.addSlotToContainer(new Slot(tedf, 25, 80, 90));
		this.addSlotToContainer(new Slot(tedf, 26, 98, 90));
		this.addSlotToContainer(new Slot(tedf, 27, 116, 90));
		this.addSlotToContainer(new Slot(tedf, 28, 134, 90));
		this.addSlotToContainer(new Slot(tedf, 29, 152, 90));
		//Water In
		this.addSlotToContainer(new Slot(tedf, 30, 8, 90 - 18));
		//Water Out
		this.addSlotToContainer(new Slot(tedf, 31, 8, 90));
		//Coolant In
		this.addSlotToContainer(new Slot(tedf, 32, 26, 90 - 18));
		//Coolant Out
		this.addSlotToContainer(new Slot(tedf, 33, 26, 90));
		//Batteries
		this.addSlotToContainer(new Slot(tedf, 34, 44, 90));
		//Fuse
		this.addSlotToContainer(new Slot(tedf, 35, 8, 108));
		
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
			
            if (par2 <= 33) {
				if (!this.mergeItemStack(var5, 34, this.inventorySlots.size(), true))
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
