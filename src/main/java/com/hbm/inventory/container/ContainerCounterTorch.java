package com.hbm.inventory.container;

import com.hbm.tileentity.network.TileEntityRadioTorchCounter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCounterTorch extends Container {
	
	protected TileEntityRadioTorchCounter radio;
	
	public ContainerCounterTorch(InventoryPlayer invPlayer, TileEntityRadioTorchCounter radio) {
		this.radio = radio;
		
		for(int i = 0; i < 3; i++) {
			this.addSlotToContainer(new Slot(radio, i, 138, 18 + 44 * i));
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 12 + j * 18, 156 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 12 + i * 18, 214));
		}
	}

	@Override public ItemStack transferStackInSlot(EntityPlayer player, int slot) { return null; }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return radio.isUseableByPlayer(player);
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		//L/R: 0
		//M3: 3
		//SHIFT: 1
		//DRAG: 5
		if(index < 0 || index > 2) {
			return super.slotClick(index, button, mode, player);
		}

		Slot slot = this.getSlot(index);
		
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		
		if(slot.getHasStack())
			ret = slot.getStack().copy();
		
		if(button == 1 && mode == 0 && slot.getHasStack()) {
			radio.matcher.nextMode(radio.getWorldObj(), slot.getStack(), index);
			return ret;
			
		} else {
			slot.putStack(held != null ? held.copy() : null);
			
			if(slot.getHasStack()) {
				slot.getStack().stackSize = 1;
			}
			
			slot.onSlotChanged();
			radio.matcher.initPatternStandard(radio.getWorldObj(), slot.getStack(), index);
			
			return ret;
		}
	}
}
