package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.inventory.SlotTakeOnly;
import com.hbm.tileentity.machine.storage.TileEntityBarrel;

import net.minecraft.entity.player.InventoryPlayer;

public class ContainerBarrel extends ContainerBase {
	
	public ContainerBarrel(InventoryPlayer invPlayer, TileEntityBarrel tedf) {
		super(invPlayer, tedf);
		
		this.addSlotToContainer(new SlotNonRetarded(tile, 0, 8, 17));
		this.addSlotToContainer(new SlotTakeOnly(tile, 1, 8, 53));
		this.addSlotToContainer(new SlotNonRetarded(tile, 2, 53 - 18, 17));
		this.addSlotToContainer(new SlotTakeOnly(tile, 3, 53 - 18, 53));
		this.addSlotToContainer(new SlotNonRetarded(tile, 4, 125, 17));
		this.addSlotToContainer(new SlotTakeOnly(tile, 5, 125, 53));
		
		this.playerInv(invPlayer, 84);
	}
}
