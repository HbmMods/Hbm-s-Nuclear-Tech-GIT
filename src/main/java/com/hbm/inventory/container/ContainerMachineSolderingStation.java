package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineSolderingStation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineSolderingStation extends Container {
	
	private TileEntityMachineSolderingStation solderer;

	public ContainerMachineSolderingStation(InventoryPlayer playerInv, TileEntityMachineSolderingStation tile) {
		solderer = tile;
		
		//Inputs
		for(int i = 0; i < 2; i++) for(int j = 0; j < 3; j++) this.addSlotToContainer(new Slot(tile, i * 3 + j, 17 + j * 18, 18 + i * 18));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 6, 107, 27));
		//Battery
		this.addSlotToContainer(new Slot(tile, 7, 152, 72));
		//Fluid ID
		this.addSlotToContainer(new Slot(tile, 8, 17, 63));
		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(tile, 9, 89, 63));
		this.addSlotToContainer(new SlotUpgrade(tile, 10, 107, 63));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 180));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return solderer.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}
}
