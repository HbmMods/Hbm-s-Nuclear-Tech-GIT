package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineTurbofan extends Container {
	
	private TileEntityMachineTurbofan diFurnace;
	private int afterburner;
	
	public ContainerMachineTurbofan(InventoryPlayer invPlayer, TileEntityMachineTurbofan tedf) {
		afterburner = 0;
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new Slot(tedf, 0, 17, 17));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 1, 17, 53));
		this.addSlotToContainer(new Slot(tedf, 2, 98, 71));
		this.addSlotToContainer(new Slot(tedf, 3, 143, 71));
		this.addSlotToContainer(new Slot(tedf, 4, 44, 71));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 121 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 179));
		}
	}
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 1, this.diFurnace.afterburner);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();

			if(index <= 4) {
				if(!this.mergeItemStack(stack, 5, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(rStack.getItem() instanceof IBatteryItem) {
					if(!this.mergeItemStack(stack, 3, 4, false)) return null;
				} else if(rStack.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(stack, 4, 5, false)) return null;
				} else if(rStack.getItem() instanceof ItemMachineUpgrade ) {
					if(!this.mergeItemStack(stack, 2, 3, false)) return null;
				} else {
					if(!this.mergeItemStack(stack, 0, 1, false)) return null;
				}
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return rStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting par1 = (ICrafting)this.crafters.get(i);
			
			if(this.afterburner != this.diFurnace.afterburner)
			{
				par1.sendProgressBarUpdate(this, 1, this.diFurnace.afterburner);
			}
		}
		
		this.afterburner = this.diFurnace.afterburner;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			diFurnace.afterburner = j;
		}
	}

}
