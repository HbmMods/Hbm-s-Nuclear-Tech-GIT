package com.hbm.inventory.container;

import com.hbm.tileentity.bomb.TileEntityNukeN2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNukeN2 extends Container {

private TileEntityNukeN2 nukeSol;
	
	public ContainerNukeN2(InventoryPlayer invPlayer, TileEntityNukeN2 tedf) {
		
		nukeSol = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 98, 36));
		this.addSlotToContainer(new Slot(tedf, 1, 116, 36));
		this.addSlotToContainer(new Slot(tedf, 2, 134, 36));
		this.addSlotToContainer(new Slot(tedf, 3, 98, 54));
		this.addSlotToContainer(new Slot(tedf, 4, 116, 54));
		this.addSlotToContainer(new Slot(tedf, 5, 134, 54));
		this.addSlotToContainer(new Slot(tedf, 6, 98, 72));
		this.addSlotToContainer(new Slot(tedf, 7, 116, 72));
		this.addSlotToContainer(new Slot(tedf, 8, 134, 72));
		this.addSlotToContainer(new Slot(tedf, 9, 98, 90));
		this.addSlotToContainer(new Slot(tedf, 10, 116, 90));
		this.addSlotToContainer(new Slot(tedf, 11, 134, 90));
		
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
			
            if (par2 <= 11) {
				if (!this.mergeItemStack(var5, 12, this.inventorySlots.size(), true))
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
