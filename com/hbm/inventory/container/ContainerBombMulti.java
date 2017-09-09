package com.hbm.inventory.container;

import com.hbm.tileentity.bomb.TileEntityBombMulti;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBombMulti extends Container {

private TileEntityBombMulti bombMulti;
	
	public ContainerBombMulti(InventoryPlayer invPlayer, TileEntityBombMulti tedf) {
		
		bombMulti = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 44, 26));
		this.addSlotToContainer(new Slot(tedf, 1, 62, 26));
		this.addSlotToContainer(new Slot(tedf, 2, 80, 26));
		this.addSlotToContainer(new Slot(tedf, 3, 44, 44));
		this.addSlotToContainer(new Slot(tedf, 4, 62, 44));
		this.addSlotToContainer(new Slot(tedf, 5, 80, 44));
		
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
			
            if (par2 <= 5) {
				if (!this.mergeItemStack(var5, 6, this.inventorySlots.size(), true))
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
		return bombMulti.isUseableByPlayer(player);
	}

}
