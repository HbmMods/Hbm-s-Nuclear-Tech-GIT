package com.hbm.inventory.container;

import com.hbm.tileentity.machine.storage.TileEntityCrateTungsten;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

//@invtweaks.api.container.ChestContainer
public class ContainerCrateTungsten extends ContainerCrateBase {

	public ContainerCrateTungsten(InventoryPlayer invPlayer, TileEntityCrateTungsten te) {
		super(te);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(te, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 32 + i * 18 + (18 * 3)));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 90 + (18 * 3)));
		}
	}
}
