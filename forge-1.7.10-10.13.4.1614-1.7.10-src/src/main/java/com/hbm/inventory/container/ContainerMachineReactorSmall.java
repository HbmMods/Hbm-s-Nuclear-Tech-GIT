package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineReactorSmall extends Container {

private TileEntityMachineReactorSmall seleniumEngine;
	
	public ContainerMachineReactorSmall(InventoryPlayer invPlayer, TileEntityMachineReactorSmall tedf) {
		
		seleniumEngine = tedf;
		
		//Rods
		this.addSlotToContainer(new Slot(tedf, 0, 98, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 134, 18));
		this.addSlotToContainer(new Slot(tedf, 2, 80, 36));
		this.addSlotToContainer(new Slot(tedf, 3, 116, 36));
		this.addSlotToContainer(new Slot(tedf, 4, 152, 36));
		this.addSlotToContainer(new Slot(tedf, 5, 98, 54));
		this.addSlotToContainer(new Slot(tedf, 6, 134, 54));
		this.addSlotToContainer(new Slot(tedf, 7, 80, 72));
		this.addSlotToContainer(new Slot(tedf, 8, 116, 72));
		this.addSlotToContainer(new Slot(tedf, 9, 152, 72));
		this.addSlotToContainer(new Slot(tedf, 10, 98, 90));
		this.addSlotToContainer(new Slot(tedf, 11, 134, 90));
		
		//Fluid IO
		this.addSlotToContainer(new Slot(tedf, 12, 8, 90));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 13, 8, 108));
		this.addSlotToContainer(new Slot(tedf, 14, 26, 90));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 15, 26, 108));
		
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
			
            if (par2 <= 16) {
				if (!this.mergeItemStack(var5, 17, this.inventorySlots.size(), true))
				{
					return null;
				}
			} else {
				if (!this.mergeItemStack(var5, 0, 13, true))
					if (!this.mergeItemStack(var5, 14, 15, true))
						if (!this.mergeItemStack(var5, 16, 17, true))
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
		return seleniumEngine.isUseableByPlayer(player);
	}
}
