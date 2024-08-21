package com.hbm.inventory.container;

import com.hbm.tileentity.machine.storage.TileEntityCrateTemplate;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

@invtweaks.api.container.ChestContainer(rowSize = 9, isLargeChest = false)
public class ContainerCrateTemplate extends ContainerCrateBase {
	
	public ContainerCrateTemplate(InventoryPlayer invPlayer, TileEntityCrateTemplate tedf) {
		super(invPlayer,tedf);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(tedf, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}

		this.playerInv(invPlayer, 8, 86, 144);
	}
}