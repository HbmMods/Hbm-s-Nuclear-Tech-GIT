package com.hbm.inventory.container;

import com.hbm.tileentity.bomb.TileEntityNukeSolinium;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNukeSolinium extends Container {

private TileEntityNukeSolinium nukeSol;
	
	public ContainerNukeSolinium(InventoryPlayer invPlayer, TileEntityNukeSolinium tedf) {
		
		nukeSol = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 26, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 53, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 107, 18));
		this.addSlotToContainer(new Slot(tedf, 3, 134, 18));
		this.addSlotToContainer(new Slot(tedf, 4, 80, 36));
		this.addSlotToContainer(new Slot(tedf, 5, 26, 54));
		this.addSlotToContainer(new Slot(tedf, 6, 53, 54));
		this.addSlotToContainer(new Slot(tedf, 7, 107, 54));
		this.addSlotToContainer(new Slot(tedf, 8, 134, 54));
		
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
			
            if (par2 <= 8) {
				if (!this.mergeItemStack(var5, 9, this.inventorySlots.size(), true))
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
		return nukeSol.isUseableByPlayer(player);
	}
}
