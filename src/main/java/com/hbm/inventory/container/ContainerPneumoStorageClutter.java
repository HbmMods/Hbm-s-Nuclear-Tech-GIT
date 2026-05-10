package com.hbm.inventory.container;

import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageClutter;

import net.minecraft.entity.player.InventoryPlayer;

public class ContainerPneumoStorageClutter extends ContainerBase {

	public ContainerPneumoStorageClutter(InventoryPlayer invPlayer, TileEntityPneumoStorageClutter storage) {
		super(invPlayer, storage);
		
		addSlots(storage, 0, 8, 17, 6, 9);
		playerInv(invPlayer, 153);
	}
}
