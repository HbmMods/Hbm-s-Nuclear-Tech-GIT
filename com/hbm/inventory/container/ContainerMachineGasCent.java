package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineGasCent extends Container {
	
	private TileEntityMachineGasCent diFurnace;
	
	public ContainerMachineGasCent(InventoryPlayer invPlayer, TileEntityMachineGasCent tedf) {
		
		diFurnace = tedf;

		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 8, 53));
		//Fluid ID IO
		this.addSlotToContainer(new Slot(tedf, 1, 35, 17));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 2, 35, 53));
		//Fluid IO
		this.addSlotToContainer(new Slot(tedf, 3, 71, 17));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 4, 71, 53));
		//Output
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 5, 134, 17));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 6, 152, 17));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 7, 134, 53));
		this.addSlotToContainer(new SlotMachineOutput(invPlayer.player, tedf, 8, 152, 53));
		
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
			
            if (par2 <= 8) {
				if (!this.mergeItemStack(var5, 9, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 2, false))
			{
				if (!this.mergeItemStack(var5, 3, 4, false))
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
