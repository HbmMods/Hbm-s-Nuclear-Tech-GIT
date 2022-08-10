package com.hbm.inventory.container;

import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.network.TileEntityCraneUnboxer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerCraneUnboxer extends Container {
	
	protected TileEntityCraneUnboxer unboxer;
	
	public ContainerCraneUnboxer(InventoryPlayer invPlayer, TileEntityCraneUnboxer unboxer) {
		this.unboxer = unboxer;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 7; j++) {
				this.addSlotToContainer(new Slot(unboxer, j + i * 7, 8 + j * 18, 17 + i * 18));
			}
		}
		
		//upgrades
		this.addSlotToContainer(new SlotUpgrade(unboxer, 21, 152, 23));
		this.addSlotToContainer(new SlotUpgrade(unboxer, 22, 152, 47));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 103 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 161));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return unboxer.isUseableByPlayer(player);
	}
}
