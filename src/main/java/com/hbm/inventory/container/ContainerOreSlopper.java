package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.tileentity.machine.TileEntityMachineOreSlopper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerOreSlopper extends Container {
	
	public TileEntityMachineOreSlopper slopper;

	public ContainerOreSlopper(InventoryPlayer player, TileEntityMachineOreSlopper slopper) {
		this.slopper = slopper;

		//Battery
		this.addSlotToContainer(new Slot(slopper, 0, 8, 72));
		//Fluid ID
		this.addSlotToContainer(new Slot(slopper, 1, 26, 72));
		//Input
		this.addSlotToContainer(new Slot(slopper, 2, 71, 27));
		//Outputs
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 3, 134, 18));
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 4, 152, 18));
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 5, 134, 36));
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 6, 152, 36));
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 7, 134, 54));
		this.addSlotToContainer(new SlotCraftingOutput(player.player, slopper, 8, 152, 54));
		//Upgrades
		this.addSlotToContainer(new Slot(slopper, 0, 62, 72));
		this.addSlotToContainer(new Slot(slopper, 0, 80, 72));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 180));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return slopper.isUseableByPlayer(player);
	}
}
