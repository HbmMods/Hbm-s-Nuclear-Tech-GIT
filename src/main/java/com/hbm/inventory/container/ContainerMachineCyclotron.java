package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCyclotron extends Container {

	private TileEntityMachineCyclotron cyclotron;
	
	public ContainerMachineCyclotron(InventoryPlayer invPlayer, TileEntityMachineCyclotron tile) {
		
		cyclotron = tile;
		
		//Input
		this.addSlotToContainer(new Slot(tile, 0, 17, 18));
		this.addSlotToContainer(new Slot(tile, 1, 17, 36));
		this.addSlotToContainer(new Slot(tile, 2, 17, 54));
		//Targets
		this.addSlotToContainer(new Slot(tile, 3, 107, 18));
		this.addSlotToContainer(new Slot(tile, 4, 107, 36));
		this.addSlotToContainer(new Slot(tile, 5, 107, 54));
		//Output
		this.addSlotToContainer(new SlotMachineOutput(tile, 6, 143, 18));
		this.addSlotToContainer(new SlotMachineOutput(tile, 7, 143, 36));
		this.addSlotToContainer(new SlotMachineOutput(tile, 8, 143, 54));
		//AMAT In
		this.addSlotToContainer(new Slot(tile, 9, 143, 90));
		//AMAT Out
		this.addSlotToContainer(new SlotMachineOutput(tile, 10, 143, 108));
		//Coolant In
		this.addSlotToContainer(new Slot(tile, 11, 62, 72));
		//Coolant Out
		this.addSlotToContainer(new SlotMachineOutput(tile, 12, 62, 90));
		//Battery
		this.addSlotToContainer(new Slot(tile, 13, 62, 108));
		//Upgrades
		this.addSlotToContainer(new Slot(tile, 14, 17, 90));
		this.addSlotToContainer(new Slot(tile, 15, 17, 108));
		
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
		/*ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 15) {
				if (!this.mergeItemStack(var5, 16, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 16, false))
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
		
		return var3;*/
		
		return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return cyclotron.isUseableByPlayer(player);
	}
}
