package com.hbm.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

@invtweaks.api.container.ChestContainer(rowSize = 13, isLargeChest = false)
public class ContainerCrateDesh extends ContainerCrateBase {

	public ContainerCrateDesh(InventoryPlayer invPlayer, IInventory tedf) {
		super(invPlayer,tedf);

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 13; j++) {
				this.addSlotToContainer(new Slot(tedf, j + i * 13, 8 + j * 18, 18 + i * 18));
			}
		}

		this.playerInv(invPlayer, 44, 174, 232);

	}
}
