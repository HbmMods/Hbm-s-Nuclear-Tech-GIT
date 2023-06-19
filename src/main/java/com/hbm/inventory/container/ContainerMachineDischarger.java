package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.tileentity.machine.TileEntityMachineDischarger;
import com.hbm.util.InventoryUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineDischarger extends Container {

private TileEntityMachineDischarger nukeBoy;
	
	public ContainerMachineDischarger(InventoryPlayer invPlayer, TileEntityMachineDischarger tedf) {
		
		nukeBoy = tedf;

		this.addSlotToContainer(new Slot(tedf, 0, 87, 63));
		//this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 1, 134, 63));
		//this.addSlotToContainer(new Slot(tedf, 2, 26, 18));
		this.addSlotToContainer(new Slot(tedf, 1, 8, 108));
		
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
			
			if(par2 <= nukeBoy.getSizeInventory() - 1) {
				if(!InventoryUtil.mergeItemStack(this.inventorySlots, var5, nukeBoy.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!InventoryUtil.mergeItemStack(this.inventorySlots, var5, 0, nukeBoy.getSizeInventory(), false)) {
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
		return nukeBoy.isUseableByPlayer(player);
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			nukeBoy.power = j;
		}
	}
}
