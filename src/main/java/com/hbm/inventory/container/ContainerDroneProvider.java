package com.hbm.inventory.container;

import com.hbm.tileentity.network.TileEntityDroneProvider;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerDroneProvider extends ContainerCrateBase {
	
	public ContainerDroneProvider(InventoryPlayer invPlayer, TileEntityDroneProvider tedf) {
		super(invPlayer,tedf);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new Slot(tedf, j + i * 3, 62 + j * 18, 17 + i * 18));
			}
		}

		this.playerInv(invPlayer, 8, 103, 161);
	}
}
