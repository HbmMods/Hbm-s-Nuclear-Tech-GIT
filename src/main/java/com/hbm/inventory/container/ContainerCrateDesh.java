package com.hbm.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerCrateDesh extends ContainerCrateBase {

	public ContainerCrateDesh(InventoryPlayer invPlayer, IInventory tedf) {
		super(tedf);

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 13; j++) {
				this.addSlotToContainer(new Slot(tedf, j + i * 13, 8 + j * 18, 18 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 44 + j * 18, 174 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 44 + i * 18, 232));
		}
	}
}
