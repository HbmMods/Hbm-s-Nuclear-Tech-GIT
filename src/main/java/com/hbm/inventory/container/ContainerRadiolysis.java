package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.tileentity.machine.TileEntityMachineRadiolysis;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRadiolysis extends Container {
	
	private TileEntityMachineRadiolysis radiolysis;
	
	public ContainerRadiolysis(InventoryPlayer playerInv, TileEntityMachineRadiolysis tile) {
		radiolysis = tile;
		
		//RTG
		for(byte i = 0; i < 2; i++) {
			for(byte j = 0; j < 5; j++) {
				this.addSlotToContainer(new Slot(tile, j + i * 5, 188 + i * 18, 8 + j * 18));
			}
		}
		
		//Fluid IO
		this.addSlotToContainer(new Slot(tile, 10, 34, 17));
		this.addSlotToContainer(new SlotTakeOnly(tile, 11, 34, 53));
		
		//Sterilization
		this.addSlotToContainer(new Slot(tile, 12, 148, 17));
		this.addSlotToContainer(new SlotTakeOnly(tile, 13, 148, 53));
		
		//Battery
		this.addSlotToContainer(new Slot(tile, 14, 8, 53));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return radiolysis.isUseableByPlayer(player);
	}
	
	/** my eye, my eye, coctor coctor coctor **/
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack var3 = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			var3 = stack.copy();

			if(index <= 14) {
				if(!this.mergeItemStack(stack, 15, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(stack, 0, 15, false)) {
				return null;
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return var3;
	}
}
