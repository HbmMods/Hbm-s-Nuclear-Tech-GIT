package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.tileentity.machine.TileEntityElectrolyser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerElectrolyserFluid extends Container {

	private TileEntityElectrolyser electrolyser;

	public ContainerElectrolyserFluid(InventoryPlayer invPlayer, TileEntityElectrolyser tedf) {
		electrolyser = tedf;

		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 186, 109));
		//Upgrades
		this.addSlotToContainer(new Slot(tedf, 1, 186, 140));
		this.addSlotToContainer(new Slot(tedf, 2, 186, 158));
		//Fluid ID
		this.addSlotToContainer(new Slot(tedf, 3, 6, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 4, 6, 54));
		//Input
		this.addSlotToContainer(new Slot(tedf, 5, 24, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 6, 24, 54));
		//Output
		this.addSlotToContainer(new Slot(tedf, 7, 78, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 8, 78, 54));
		this.addSlotToContainer(new Slot(tedf, 9, 134, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 10, 134, 54));
		//Byproducts
		this.addSlotToContainer(new SlotTakeOnly(tedf, 11, 154, 18));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 12, 154, 36));
		this.addSlotToContainer(new SlotTakeOnly(tedf, 13, 154, 54));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 180));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return electrolyser.isUseableByPlayer(player);
	}
}
