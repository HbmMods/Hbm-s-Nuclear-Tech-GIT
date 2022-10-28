package com.hbm.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

//@invtweaks.api.container.ChestContainer
public class ContainerCrateSteel extends ContainerCrateBase {

	public ContainerCrateSteel(InventoryPlayer invPlayer, IInventory tedf) {
		super(tedf);

		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(tedf, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + (18 * 3) + 2));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + (18 * 3) + 2));
		}
	}
}
