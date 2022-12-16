package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineExcavator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerMachineExcavator extends Container {
	
	TileEntityMachineExcavator excavator;

	public ContainerMachineExcavator(InventoryPlayer invPlayer, TileEntityMachineExcavator tile) {
		this.excavator = tile;

		//Battery
		this.addSlotToContainer(new Slot(tile, 0, 220, 72));
		//Fluid ID
		this.addSlotToContainer(new Slot(tile, 1, 202, 72));
		//Upgrades
		for(int i = 0; i < 3; i++) {
			this.addSlotToContainer(new Slot(tile, 2 + i, 136 + i * 18, 75));
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new SlotMachineOutput(tile, 5 + j + i * 3, 136 + j * 18, 5 + i * 18));
			}
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 41 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 41 + i * 18, 180));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return false;
	}
}
