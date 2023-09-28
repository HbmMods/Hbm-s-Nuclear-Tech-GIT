package com.hbm.inventory.container;

import com.hbm.tileentity.network.TileEntityDroneDock;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerDroneDock extends ContainerCrateBase {
	
	public ContainerDroneDock(InventoryPlayer invPlayer, TileEntityDroneDock tedf) {
		super(tedf);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new Slot(tedf, j + i * 3, 62 + j * 18, 17 + i * 18));
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
}
