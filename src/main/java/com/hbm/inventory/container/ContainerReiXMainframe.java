package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityReiXMainframe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerReiXMainframe extends Container {
	
	private TileEntityReiXMainframe diFurnace;
	
	private int power;
	
	public ContainerReiXMainframe(InventoryPlayer invPlayer, TileEntityReiXMainframe tedf) {
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 8, 158));
		this.addSlotToContainer(new Slot(tedf, 1, 44, 158));
		this.addSlotToContainer(new Slot(tedf, 2, 80, 158));
		this.addSlotToContainer(new Slot(tedf, 3, 116, 158));
		this.addSlotToContainer(new Slot(tedf, 4, 152, 158));
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
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
			
            if (par2 <= 4) {
				if (!this.mergeItemStack(var5, 5, this.inventorySlots.size(), true))
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
		return diFurnace.isUseableByPlayer(player);
	}
}
