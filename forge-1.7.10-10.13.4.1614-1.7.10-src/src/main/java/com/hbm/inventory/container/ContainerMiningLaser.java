package com.hbm.inventory.container;

import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMiningLaser extends Container {
	
	private TileEntityMachineMiningLaser diFurnace;
	
	public ContainerMiningLaser(InventoryPlayer invPlayer, TileEntityMachineMiningLaser tedf) {
		diFurnace = tedf;

		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 8, 108));
		//Upgrades
		for(int i = 0; i < 2; i++)
			for(int j = 0; j < 4; j++)
				this.addSlotToContainer(new SlotUpgrade(tedf, 1 + i * 4 + j, 98 + j * 18, 18 + i * 18));
		//Output
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 7; j++)
				this.addSlotToContainer(new Slot(tedf, 9 + i * 7 + j, 44 + j * 18, 72 + i * 18));
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
		
		for(int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
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
			
            if (par2 <= diFurnace.getSizeInventory() - 1) {
				if (!this.mergeItemStack(var5, diFurnace.getSizeInventory(), this.inventorySlots.size(), true))
				{
					return null;
				}
			} else {
				
				if (!this.mergeItemStack(var5, 0, 9, false))
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
