package com.hbm.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

@invtweaks.api.container.ChestContainer(rowSize = 9, isLargeChest = false)
public class ContainerCrateSteel extends ContainerCrateBase {

	public ContainerCrateSteel(InventoryPlayer invPlayer, IInventory tedf) {
		super(invPlayer,tedf);

		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(tedf, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}

		this.playerInv(invPlayer, 8, 84 + (18 * 3) + 2, 142 + (18 * 3) + 2);
	}
}
