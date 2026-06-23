package com.hbm.inventory.container;

import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageImporter;

import net.minecraft.entity.player.InventoryPlayer;

public class ContainerPneumoStorageImporter extends ContainerBase {

	public ContainerPneumoStorageImporter(InventoryPlayer invPlayer, TileEntityPneumoStorageImporter importer) {
		super(invPlayer, importer);
		
		addSlots(importer, 0, 62, 17, 3, 3);
		playerInv(invPlayer, 103);
	}
}
