package com.hbm.inventory.container;

import com.hbm.tileentity.bomb.TileEntityAntimatter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNukeAntimatter extends Container {

private TileEntityAntimatter SillyBomb;
	
	public ContainerNukeAntimatter(InventoryPlayer invPlayer, TileEntityAntimatter tedf) {
		
		SillyBomb = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 26, 15));
		this.addSlotToContainer(new Slot(tedf, 1, 134, 14));
		this.addSlotToContainer(new Slot(tedf, 2, 81, 15));
		this.addSlotToContainer(new Slot(tedf, 3, 26, 44));
		this.addSlotToContainer(new Slot(tedf, 4, 134, 43));
		
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
			
            if (par2 <= 2) {
				if (!this.mergeItemStack(var5, 2, this.inventorySlots.size(), true))
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
		return SillyBomb.isUseableByPlayer(player);
	}
}
