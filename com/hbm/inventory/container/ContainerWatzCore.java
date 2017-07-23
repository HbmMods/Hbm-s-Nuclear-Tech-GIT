package com.hbm.inventory.container;

import com.hbm.tileentity.TileEntityWatzCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWatzCore extends Container {
	
	private TileEntityWatzCore diFurnace;
	
	private int water;
	private int power;
	private int trit;
	
	public ContainerWatzCore(InventoryPlayer invPlayer, TileEntityWatzCore tedf) {
		
		diFurnace = tedf;
		
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
		this.addSlotToContainer(new Slot(tedf, 12, 8, 54));
		this.addSlotToContainer(new Slot(tedf, 13, 26, 54));
		this.addSlotToContainer(new Slot(tedf, 14, 44, 54));
		this.addSlotToContainer(new Slot(tedf, 15, 62, 54));
		this.addSlotToContainer(new Slot(tedf, 16, 80, 54));
		this.addSlotToContainer(new Slot(tedf, 17, 98, 54));
		this.addSlotToContainer(new Slot(tedf, 18, 8, 72));
		this.addSlotToContainer(new Slot(tedf, 19, 26, 72));
		this.addSlotToContainer(new Slot(tedf, 20, 44, 72));
		this.addSlotToContainer(new Slot(tedf, 21, 62, 72));
		this.addSlotToContainer(new Slot(tedf, 22, 80, 72));
		this.addSlotToContainer(new Slot(tedf, 23, 98, 72));
		this.addSlotToContainer(new Slot(tedf, 24, 8, 90));
		this.addSlotToContainer(new Slot(tedf, 25, 26, 90));
		this.addSlotToContainer(new Slot(tedf, 26, 44, 90));
		this.addSlotToContainer(new Slot(tedf, 27, 62, 90));
		this.addSlotToContainer(new Slot(tedf, 28, 80, 90));
		this.addSlotToContainer(new Slot(tedf, 29, 98, 90));
		this.addSlotToContainer(new Slot(tedf, 30, 8, 108));
		this.addSlotToContainer(new Slot(tedf, 31, 26, 108));
		this.addSlotToContainer(new Slot(tedf, 32, 44, 108));
		this.addSlotToContainer(new Slot(tedf, 33, 62, 108));
		this.addSlotToContainer(new Slot(tedf, 34, 80, 108));
		this.addSlotToContainer(new Slot(tedf, 35, 98, 108));
		this.addSlotToContainer(new Slot(tedf, 36, 134, 108));
		this.addSlotToContainer(new Slot(tedf, 37, 152, 108));
		this.addSlotToContainer(new Slot(tedf, 38, 116, 63));
		
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
		crafting.sendProgressBarUpdate(this, 0, this.diFurnace.waste);
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.power);
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
			
            if (par2 <= 38) {
				if (!this.mergeItemStack(var5, 39, this.inventorySlots.size(), true))
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
			
			if(this.water != this.diFurnace.waste)
			{
				par1.sendProgressBarUpdate(this, 0, this.diFurnace.waste);
			}
			
			if(this.power != this.diFurnace.power)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.power);
			}
		}
		
		this.water = this.diFurnace.waste;
		this.power = this.diFurnace.power;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			diFurnace.waste = j;
		}
		if(i == 1)
		{
			diFurnace.power = j;
		}
	}
}
