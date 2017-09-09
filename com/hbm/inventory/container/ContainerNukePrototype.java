package com.hbm.inventory.container;

import com.hbm.tileentity.bomb.TileEntityNukePrototype;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNukePrototype extends Container {

private TileEntityNukePrototype nukeTsar;
	
	public ContainerNukePrototype(InventoryPlayer invPlayer, TileEntityNukePrototype tedf) {
		
		nukeTsar = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 8, 35));
		this.addSlotToContainer(new Slot(tedf, 1, 26, 35));
		this.addSlotToContainer(new Slot(tedf, 2, 44, 26));
		this.addSlotToContainer(new Slot(tedf, 3, 44, 44));
		this.addSlotToContainer(new Slot(tedf, 4, 62, 26));
		this.addSlotToContainer(new Slot(tedf, 5, 62, 44));
		this.addSlotToContainer(new Slot(tedf, 6, 80, 26));
		this.addSlotToContainer(new Slot(tedf, 7, 80, 44));
		this.addSlotToContainer(new Slot(tedf, 8, 98, 26));
		this.addSlotToContainer(new Slot(tedf, 9, 98, 44));
		this.addSlotToContainer(new Slot(tedf, 10, 116, 26));
		this.addSlotToContainer(new Slot(tedf, 11, 116, 44));
		this.addSlotToContainer(new Slot(tedf, 12, 134, 35));
		this.addSlotToContainer(new Slot(tedf, 13, 152, 35));
		
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
		return nukeTsar.isUseableByPlayer(player);
	}
}
