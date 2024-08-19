package com.hbm.inventory.container;

import com.hbm.tileentity.machine.storage.TileEntityCrateTungsten;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

@invtweaks.api.container.ChestContainer(rowSize = 9, isLargeChest = false)
public class ContainerCrateTungsten extends ContainerCrateBase {

	public ContainerCrateTungsten(InventoryPlayer invPlayer, TileEntityCrateTungsten te) {
		super(invPlayer,te);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(te, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}

		this.playerInv(invPlayer, 8, 32 + 18 * 3, 90 + (18 * 3));
	}
}
