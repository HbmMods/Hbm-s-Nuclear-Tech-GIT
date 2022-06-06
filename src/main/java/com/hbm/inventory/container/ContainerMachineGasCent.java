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

		//Output
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				this.addSlotToContainer(new SlotMachineOutput(tedf, j + i * 2, 71 + j * 18, 53 + i * 18));
			}
		}
		
		//Battery
		this.addSlotToContainer(new Slot(tedf, 4, 182, 71));
		//Fluid ID IO
		this.addSlotToContainer(new Slot(tedf, 5, 91, 15));
		//upgrade
		this.addSlotToContainer(new Slot(tedf, 6, 69, 15));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 180));
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
		ItemStack returnStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			returnStack = stack.copy();
			
            if(index <= 6) {
				if (!this.mergeItemStack(stack, 7, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(stack, 4, 7, false)) {
				return null;
			}
			
			if (stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}
		
		return returnStack;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return gasCent.isUseableByPlayer(player);
	}
}
