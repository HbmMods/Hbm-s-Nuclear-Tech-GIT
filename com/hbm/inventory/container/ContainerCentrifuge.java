package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCentrifuge extends Container {
	
	private TileEntityMachineCentrifuge diFurnace;
	
	public ContainerCentrifuge(InventoryPlayer invPlayer, TileEntityMachineCentrifuge tedf) {
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 26, 17));
		this.addSlotToContainer(new Slot(tedf, 1, 26, 53));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 2, 134, 17));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 3, 152, 17));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 4, 134, 53));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 5, 152, 53));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
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

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUseableByPlayer(player);
	}

}