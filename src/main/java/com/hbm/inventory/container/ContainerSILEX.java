package com.hbm.inventory.container;

import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.TileEntitySILEX;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSILEX extends Container {

	private TileEntitySILEX silex;

	public ContainerSILEX(InventoryPlayer invPlayer, TileEntitySILEX te) {
		silex = te;
		
		//Input
		this.addSlotToContainer(new Slot(te, 0, 80, 12));
		//Fluid ID
		this.addSlotToContainer(new Slot(te, 1, 8, 24));
		//Fluid Container
		this.addSlotToContainer(new Slot(te, 2, 8 + 18, 24));
		this.addSlotToContainer(new SlotTakeOnly(te, 3, 8 + 18*2, 24));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 4, 116, 90));
		//Output Queue
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 5, 134, 72));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 6, 152, 72));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 7, 134, 90));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 8, 152, 90));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 9, 134, 108));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 10, 152, 108));
		
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
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 10) {
				if(!this.mergeItemStack(var5, 11, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(var5, 1, 2, false)) {
						return null;
					}
				} else if(FluidContainerRegistry.getFluidContent(var3, silex.tank.getTankType()) > 0) {
					if(!this.mergeItemStack(var5, 2, 3, false)) {
						return null;
					}
				} else {
					if(!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				}
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return silex.isUseableByPlayer(player);
	}
}