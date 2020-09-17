package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineSatDock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSatDock extends Container {
	
	private TileEntityMachineSatDock diFurnace;
	
	public ContainerSatDock(InventoryPlayer invPlayer, TileEntityMachineSatDock tedf) {
		
		diFurnace = tedf;

		//Storage
		this.addSlotToContainer(new Slot(tedf, 0, 62, 17));
		this.addSlotToContainer(new Slot(tedf, 1, 80, 17));
		this.addSlotToContainer(new Slot(tedf, 2, 98, 17));
		this.addSlotToContainer(new Slot(tedf, 3, 116, 17));
		this.addSlotToContainer(new Slot(tedf, 4, 134, 17));
		this.addSlotToContainer(new Slot(tedf, 5, 62, 35));
		this.addSlotToContainer(new Slot(tedf, 6, 80, 35));
		this.addSlotToContainer(new Slot(tedf, 7, 98, 35));
		this.addSlotToContainer(new Slot(tedf, 8, 116, 35));
		this.addSlotToContainer(new Slot(tedf, 9, 134, 35));
		this.addSlotToContainer(new Slot(tedf, 10, 62, 53));
		this.addSlotToContainer(new Slot(tedf, 11, 80, 53));
		this.addSlotToContainer(new Slot(tedf, 12, 98, 53));
		this.addSlotToContainer(new Slot(tedf, 13, 116, 53));
		this.addSlotToContainer(new Slot(tedf, 14, 134, 53));
		//Chip
		this.addSlotToContainer(new Slot(tedf, 15, 26, 35));
		
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
		return diFurnace.isUseableByPlayer(player);
	}
}
