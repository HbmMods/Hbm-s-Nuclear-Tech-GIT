package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.tileentity.machine.oil.TileEntityMachineLiquefactor;

import net.minecraft.entity.player.InventoryPlayer;

public class ContainerLiquefactor extends ContainerBase {
	
	public ContainerLiquefactor(InventoryPlayer playerInv, TileEntityMachineLiquefactor tile) {
		super(playerInv, tile);
		
		//Input
		this.addSlotToContainer(new SlotNonRetarded(tile, 0, 35, 54));
		//Battery
		this.addSlotToContainer(new SlotNonRetarded(tile, 1, 134, 72));
		//Upgrades
		this.addSlotToContainer(new SlotNonRetarded(tile, 2, 98, 36));
		this.addSlotToContainer(new SlotNonRetarded(tile, 3, 98, 54));

		this.playerInv(playerInv, 122);
	}
}
