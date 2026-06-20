package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageMono;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPneumoStorageMono extends ContainerBase {

	public ContainerPneumoStorageMono(InventoryPlayer invPlayer, TileEntityPneumoStorageMono storage) {
		super(invPlayer, storage);
		
		for(int i = 0; i < 3; i++) {
			this.addSlotToContainer(new SlotPattern(storage, i, 8, 17 + i * 18));
		}
		
		playerInv(invPlayer, 99);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		return null;
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		//L/R: 0
		//M3: 3
		//SHIFT: 1
		//DRAG: 5
		
		if(index < 0 || index >= 3) {
			return super.slotClick(index, button, mode, player);
		}
		
		TileEntityPneumoStorageMono mono = (TileEntityPneumoStorageMono) this.tile;
		if(mono.amounts[index] > 0) return null;
		
		Slot slot = this.getSlot(index);
		
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		
		if(slot.getHasStack()) ret = slot.getStack().copy();
		slot.putStack(held);
		return ret;
	}
}
