package com.hbm.inventory.container;

import com.hbm.tileentity.bomb.TileEntityNukeCustom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNukeCustom extends Container {

private TileEntityNukeCustom nukeBoy;
	
	public ContainerNukeCustom(InventoryPlayer invPlayer, TileEntityNukeCustom tedf) {
		
		nukeBoy = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 8, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 26, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 44, 18));
		this.addSlotToContainer(new Slot(tedf, 3, 62, 18));
		this.addSlotToContainer(new Slot(tedf, 4, 80, 18));
		this.addSlotToContainer(new Slot(tedf, 5, 98, 18));
		this.addSlotToContainer(new Slot(tedf, 6, 116, 18));
		this.addSlotToContainer(new Slot(tedf, 7, 134, 18));
		this.addSlotToContainer(new Slot(tedf, 8, 152, 18));
		this.addSlotToContainer(new Slot(tedf, 9, 8, 36));
		this.addSlotToContainer(new Slot(tedf, 10, 26, 36));
		this.addSlotToContainer(new Slot(tedf, 11, 44, 36));
		this.addSlotToContainer(new Slot(tedf, 12, 62, 36));
		this.addSlotToContainer(new Slot(tedf, 13, 80, 36));
		this.addSlotToContainer(new Slot(tedf, 14, 98, 36));
		this.addSlotToContainer(new Slot(tedf, 15, 116, 36));
		this.addSlotToContainer(new Slot(tedf, 16, 134, 36));
		this.addSlotToContainer(new Slot(tedf, 17, 152, 36));
		this.addSlotToContainer(new Slot(tedf, 18, 8, 54));
		this.addSlotToContainer(new Slot(tedf, 19, 26, 54));
		this.addSlotToContainer(new Slot(tedf, 20, 44, 54));
		this.addSlotToContainer(new Slot(tedf, 21, 62, 54));
		this.addSlotToContainer(new Slot(tedf, 22, 80, 54));
		this.addSlotToContainer(new Slot(tedf, 23, 98, 54));
		this.addSlotToContainer(new Slot(tedf, 24, 116, 54));
		this.addSlotToContainer(new Slot(tedf, 25, 134, 54));
		this.addSlotToContainer(new Slot(tedf, 26, 152, 54));
		
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
			
            if (par2 <= 26) {
				if (!this.mergeItemStack(var5, 27, this.inventorySlots.size(), true))
				{
					return null;
				}
			} else {
				if (!this.mergeItemStack(var5, 0, 27, true))
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
		return nukeBoy.isUseableByPlayer(player);
	}
}
