package com.hbm.inventory.container;

import com.hbm.tileentity.bomb.TileEntityCelPrime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCelPrime extends Container {

private TileEntityCelPrime nukeSol;
	
	public ContainerCelPrime(InventoryPlayer invPlayer, TileEntityCelPrime tedf) {
		
		nukeSol = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 44, 158));
		this.addSlotToContainer(new Slot(tedf, 1, 80, 158));
		
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
			
            if (par2 <= 10) {
				if (!this.mergeItemStack(var5, 11, this.inventorySlots.size(), true))
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
