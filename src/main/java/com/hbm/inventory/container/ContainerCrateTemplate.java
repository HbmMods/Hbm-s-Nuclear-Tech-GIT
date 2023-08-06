package com.hbm.inventory.container;

import com.hbm.tileentity.machine.storage.TileEntityCrateTemplate;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerCrateTemplate extends ContainerCrateBase {
	
	public ContainerCrateTemplate(InventoryPlayer invPlayer, TileEntityCrateTemplate tedf) {
		super(tedf);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(tedf, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 144));
		}
	}
}