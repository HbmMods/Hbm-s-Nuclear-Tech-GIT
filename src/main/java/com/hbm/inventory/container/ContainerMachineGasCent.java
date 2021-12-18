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
	
	private TileEntityMachineGasCent gasCent;
	
	public ContainerMachineGasCent(InventoryPlayer invPlayer, TileEntityMachineGasCent tedf) {
		
		gasCent = tedf;

		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 8, 53));
		//Fluid ID IO
		this.addSlotToContainer(new Slot(tedf, 1, 30, 35));
		//Output
		this.addSlotToContainer(new SlotMachineOutput(tedf, 2, 133, 26));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 3, 133, 44));
		this.addSlotToContainer(new SlotMachineOutput(tedf, 4, 151, 35));
		//upgrade
		this.addSlotToContainer(new Slot(tedf, 5, 81, 18));
		
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
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
		ItemStack var3 = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack stack = slot.getStack();
			var3 = stack.copy();
			
            if (index <= 5) {
				if (!this.mergeItemStack(stack, 6, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(stack, 0, 2, false))
			{
				if (!this.mergeItemStack(stack, 3, 4, false))
					return null;
			}
			
			if (stack.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}
		
		return var3;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return gasCent.isUseableByPlayer(player);
	}
}
