package com.hbm.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

@invtweaks.api.container.ChestContainer(rowSize = 5, isLargeChest = false)
public class ContainerSafe extends ContainerCrateBase {

	public ContainerSafe(InventoryPlayer invPlayer, IInventory te) {
		super(invPlayer,te);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 5; j++) {
				this.addSlotToContainer(new Slot(te, j + i * 5, 8 + j * 18 + 18 * 2, 18 + i * 18));
			}
		}

		this.playerInv(invPlayer, 8, 32 + 18 * 3, 90 + (18 * 3));
	}
}
