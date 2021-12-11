package com.hbm.inventory.container;

import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.candu.TileEntityCanduCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCanduCore extends Container {
	
	private TileEntityCanduCore core;

	public ContainerCanduCore(InventoryPlayer invPlayer, TileEntityCanduCore te) {
		core = te;
		
		// get around round round i get around
		
		int rodIndex = 0;
		for(double y = -4; y <= 4; y++) {
			for(double x = -4; x <= 4; x++) {
				double result = Math.hypot(x, y);
				if(result <= 4.25D) {
					int xPos = (int)x * 18;
					int yPos = (int)y * 18;
					this.addSlotToContainer(new Slot(te, rodIndex, 80 + xPos, 81 + yPos));
					int[] rodCoord = {(int)x, (int)y};
					te.createFuelMapEntry(rodIndex, rodCoord);
					rodIndex++;
					
				}
			}
		}
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 118 + i * 18 + (18 * 3) + 2));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 176 + (18 * 3) + 2));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
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
				}
				else if (!this.mergeItemStack(var5, 0, 2, false))
				{
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
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return core.isUseableByPlayer(player);
	}

}
