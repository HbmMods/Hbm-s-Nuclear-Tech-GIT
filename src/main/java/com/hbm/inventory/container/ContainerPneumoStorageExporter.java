package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageExporter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPneumoStorageExporter extends ContainerBase {

	public ContainerPneumoStorageExporter(InventoryPlayer invPlayer, TileEntityPneumoStorageExporter exporter) {
		super(invPlayer, exporter);
		
		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new SlotPattern(exporter, i, 17 + (i % 3) * 18, 17 + (i / 3) * 18).allowStackSize());
		}
		
		addSlots(exporter, 9, 80, 17, 3, 3);
		playerInv(invPlayer, 103);
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		//L/R: 0
		//M3: 3
		//SHIFT: 1
		//DRAG: 5
		
		if(index < 0 || index >= 9) {
			return super.slotClick(index, button, mode, player);
		}
		
		Slot slot = this.getSlot(index);
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		
		if(slot.getHasStack()) ret = slot.getStack().copy();
		slot.putStack(held);
		return ret;
	}
}
