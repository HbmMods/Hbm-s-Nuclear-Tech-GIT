package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineSelenium extends Container {

private TileEntityMachineSeleniumEngine seleniumEngine;
	
	public ContainerMachineSelenium(InventoryPlayer invPlayer, TileEntityMachineSeleniumEngine tedf) {
		
		seleniumEngine = tedf;
		
		//Pistons
		this.addSlotToContainer(new Slot(tedf, 0, 8, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 26, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 44, 18));
		this.addSlotToContainer(new Slot(tedf, 3, 8, 36));
		this.addSlotToContainer(new Slot(tedf, 4, 26, 36));
		this.addSlotToContainer(new Slot(tedf, 5, 44, 36));
		this.addSlotToContainer(new Slot(tedf, 6, 8, 54));
		this.addSlotToContainer(new Slot(tedf, 7, 26, 54));
		this.addSlotToContainer(new Slot(tedf, 8, 44, 54));
		
		//Fluid IO
		this.addSlotToContainer(new Slot(tedf, 9, 80, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 10, 80, 54));
		
		//Fluid IDs
		this.addSlotToContainer(new Slot(tedf, 11, 152, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 12, 152, 54));
		
		//Battery
		this.addSlotToContainer(new Slot(tedf, 13, 116, 90));
		
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
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 13) {
				if (!this.mergeItemStack(var5, 14, this.inventorySlots.size(), true))
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
		return seleniumEngine.isUseableByPlayer(player);
	}
}
