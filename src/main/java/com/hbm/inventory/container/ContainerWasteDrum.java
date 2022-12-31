package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityWasteDrum;
import com.hbm.util.InventoryUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWasteDrum extends Container {
	
	private TileEntityWasteDrum drum;
	
	public ContainerWasteDrum(InventoryPlayer invPlayer, TileEntityWasteDrum tedf) {
		drum = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 71, 21));
		this.addSlotToContainer(new Slot(tedf, 1, 89, 21));
		this.addSlotToContainer(new Slot(tedf, 2, 53, 39));
		this.addSlotToContainer(new Slot(tedf, 3, 71, 39));
		this.addSlotToContainer(new Slot(tedf, 4, 89, 39));
		this.addSlotToContainer(new Slot(tedf, 5, 107, 39));
		this.addSlotToContainer(new Slot(tedf, 6, 53, 57));
		this.addSlotToContainer(new Slot(tedf, 7, 71, 57));
		this.addSlotToContainer(new Slot(tedf, 8, 89, 57));
		this.addSlotToContainer(new Slot(tedf, 9, 107, 57));
		this.addSlotToContainer(new Slot(tedf, 10, 71, 75));
		this.addSlotToContainer(new Slot(tedf, 11, 89, 75));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 23));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 23));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= drum.getSizeInventory() - 1) {
				if(!InventoryUtil.mergeItemStack(this.inventorySlots, var5, drum.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!InventoryUtil.mergeItemStack(this.inventorySlots, var5, 0, drum.getSizeInventory(), false)) {
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
			
			var4.onPickupFromSlot(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return drum.isUseableByPlayer(player);
	}
}
