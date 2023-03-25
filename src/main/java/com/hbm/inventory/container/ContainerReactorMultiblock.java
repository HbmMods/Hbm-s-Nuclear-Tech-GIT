package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerReactorMultiblock extends Container {
	
	private TileEntityMachineReactorLarge diFurnace;
	
	public ContainerReactorMultiblock(InventoryPlayer invPlayer, TileEntityMachineReactorLarge tedf) {
		
		diFurnace = tedf;
		
		//Water in
		this.addSlotToContainer(new Slot(tedf, 0, 8, 90));
		//Water out
		this.addSlotToContainer(new SlotMachineOutput(tedf, 1, 8, 108));
		//Coolant in
		this.addSlotToContainer(new Slot(tedf, 2, 26, 90));
		//Coolant out
		this.addSlotToContainer(new SlotMachineOutput(tedf, 3, 26, 108));
		
		//Fuel in
		this.addSlotToContainer(new Slot(tedf, 4, 80, 36));
		//Fuel out
		this.addSlotToContainer(new SlotMachineOutput(tedf, 5, 80, 72));
		//Waste in
		this.addSlotToContainer(new Slot(tedf, 6, 152, 36));
		//Waste out
		this.addSlotToContainer(new SlotMachineOutput(tedf, 7, 152, 72));
		
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
			
            if (par2 <= 7) {
				if (!this.mergeItemStack(var5, 8, this.inventorySlots.size(), true))
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
