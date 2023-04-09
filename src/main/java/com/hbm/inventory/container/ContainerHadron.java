package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.tileentity.machine.TileEntityHadron;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHadron extends Container {
	
	private TileEntityHadron hadron;
	
	public ContainerHadron(InventoryPlayer invPlayer, TileEntityHadron tedf) {
		
		hadron = tedf;

		//Inputs
		this.addSlotToContainer(new Slot(tedf, 0, 17, 36));
		this.addSlotToContainer(new Slot(tedf, 1, 35, 36));
		//Outputs
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 2, 125, 36));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 3, 143, 36));
		//Battery
		this.addSlotToContainer(new Slot(tedf, 4, 44, 108));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + (18 * 3) + 2));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + (18 * 3) + 2));
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
			} else if (!this.mergeItemStack(var5, 0, 2, false)) {
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
		return hadron.isUseableByPlayer(player);
	}
}
