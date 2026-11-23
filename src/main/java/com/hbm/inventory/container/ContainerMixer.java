package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.tileentity.machine.TileEntityMachineMixer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerMixer extends ContainerBase {
	
	public ContainerMixer(InventoryPlayer player, TileEntityMachineMixer mixer) {
		super(player, mixer);

		// Battery
		this.addSlotToContainer(new SlotNonRetarded(mixer, 0, 12, 72));
		// Item Input
		this.addSlotToContainer(new SlotNonRetarded(mixer, 1, 52, 72));
		// Fluid ID
		this.addSlotToContainer(new SlotNonRetarded(mixer, 2, 126, 72));
		// Upgrades
		this.addSlotToContainer(new SlotNonRetarded(mixer, 3, 148, 18));
		this.addSlotToContainer(new SlotNonRetarded(mixer, 4, 148, 36));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 180));
		}
	}
}
