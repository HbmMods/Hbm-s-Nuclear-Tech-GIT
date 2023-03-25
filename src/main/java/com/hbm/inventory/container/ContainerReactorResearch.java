package com.hbm.inventory.container;

import com.hbm.items.machine.ItemPlateFuel;
import com.hbm.tileentity.machine.TileEntityReactorResearch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerReactorResearch extends Container {

private TileEntityReactorResearch reactor;
	
	public ContainerReactorResearch(InventoryPlayer invPlayer, TileEntityReactorResearch tedf) {
		
		reactor = tedf;
		
		//Rods
		this.addSlotToContainer(new Slot(tedf, 0, 95, 22));
		this.addSlotToContainer(new Slot(tedf, 1, 131, 22));
		this.addSlotToContainer(new Slot(tedf, 2, 77, 40));
		this.addSlotToContainer(new Slot(tedf, 3, 113, 40));
		this.addSlotToContainer(new Slot(tedf, 4, 149, 40));
		this.addSlotToContainer(new Slot(tedf, 5, 95, 58));
		this.addSlotToContainer(new Slot(tedf, 6, 131, 58));
		this.addSlotToContainer(new Slot(tedf, 7, 77, 76));
		this.addSlotToContainer(new Slot(tedf, 8, 113, 76));
		this.addSlotToContainer(new Slot(tedf, 9, 149, 76));
		this.addSlotToContainer(new Slot(tedf, 10, 95, 94));
		this.addSlotToContainer(new Slot(tedf, 11, 131, 94));
		
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
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack var3 = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			var3 = stack.copy();
			
            if (index <= 12) {
				if (!this.mergeItemStack(stack, 13, this.inventorySlots.size(), true)){
					return null;
				}
			} else {
				if(stack.getItem() instanceof ItemPlateFuel) {
					if (!this.mergeItemStack(stack, 0, 12, true))
						return null;
				} else {
					return null;
				}
			}
			if (stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}
		
		return var3;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return reactor.isUseableByPlayer(player);
	}
}
