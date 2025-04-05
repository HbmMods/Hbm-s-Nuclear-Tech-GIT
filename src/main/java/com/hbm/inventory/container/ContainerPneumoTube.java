package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.tileentity.network.TileEntityPneumoTube;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPneumoTube extends ContainerBase {

	private TileEntityPneumoTube tube;

	public ContainerPneumoTube(InventoryPlayer invPlayer, TileEntityPneumoTube tube) {
		super(invPlayer, tube);
		this.tube = tube;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 5; j++) {
				this.addSlotToContainer(new SlotPattern(tube, i * 5 + j, 35 + j * 18, 17 + i * 18));
			}
		}
		
		playerInv(invPlayer, 8, 103, 161);
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		if(index < 0 || index >= 15) return super.slotClick(index, button, mode, player);

		Slot slot = this.getSlot(index);
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		
		if(slot.getHasStack()) ret = slot.getStack().copy();
		
		if(button == 1 && mode == 0 && slot.getHasStack()) {
			tube.nextMode(index);
			return ret;
		} else {
			slot.putStack(held != null ? held.copy() : null);
			if(slot.getHasStack()) slot.getStack().stackSize = 1;
			slot.onSlotChanged();
			tube.initPattern(slot.getStack(), index);
			return ret;
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}
}
