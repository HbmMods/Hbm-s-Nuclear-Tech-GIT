package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityWasteDrum;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWasteDrum extends Container {
	
	private TileEntityWasteDrum diFurnace;
	
	public ContainerWasteDrum(InventoryPlayer invPlayer, TileEntityWasteDrum tedf) {
		diFurnace = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 71, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 89, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 53, 36));
		this.addSlotToContainer(new Slot(tedf, 3, 71, 36));
		this.addSlotToContainer(new Slot(tedf, 4, 89, 36));
		this.addSlotToContainer(new Slot(tedf, 5, 107, 36));
		this.addSlotToContainer(new Slot(tedf, 6, 53, 54));
		this.addSlotToContainer(new Slot(tedf, 7, 71, 54));
		this.addSlotToContainer(new Slot(tedf, 8, 89, 54));
		this.addSlotToContainer(new Slot(tedf, 9, 107, 54));
		this.addSlotToContainer(new Slot(tedf, 10, 71, 72));
		this.addSlotToContainer(new Slot(tedf, 11, 89, 72));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 20));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 20));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= diFurnace.getSizeInventory() - 1) {
				if(!this.mergeItemStack(var5, diFurnace.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 0, 0, false)) {
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
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
