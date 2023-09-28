package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.tileentity.network.TileEntityDroneRequester;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDroneRequester extends ContainerCrateBase {
	
	public ContainerDroneRequester(InventoryPlayer invPlayer, TileEntityDroneRequester tedf) {
		super(tedf);
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new SlotPattern(tedf, j + i * 3, 98 + j * 18, 17 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new Slot(tedf, j + i * 3 + 9, 26 + j * 18, 17 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 103 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 161));
		}
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		//L/R: 0
		//M3: 3
		//SHIFT: 1
		//DRAG: 5
		
		if(index < 0 || index > 8) {
			return super.slotClick(index, button, mode, player);
		}

		Slot slot = this.getSlot(index);
		
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		TileEntityDroneRequester requester = (TileEntityDroneRequester) crate;
		
		if(slot.getHasStack())
			ret = slot.getStack().copy();
		
		if(button == 1 && mode == 0 && slot.getHasStack()) {
			requester.nextMode(index);
			return ret;
			
		} else {
			slot.putStack(held != null ? held.copy() : null);
			
			if(slot.getHasStack()) {
				slot.getStack().stackSize = 1;
			}
			
			slot.onSlotChanged();
			requester.matcher.initPatternStandard(requester.getWorldObj(), slot.getStack(), index);
			
			return ret;
		}
	}
}
