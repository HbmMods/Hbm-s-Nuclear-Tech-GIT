package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.tileentity.machine.TileEntityElectrolyser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerElectrolyserMetal extends Container {

	private TileEntityElectrolyser electrolyser;

	public ContainerElectrolyserMetal(InventoryPlayer invPlayer, TileEntityElectrolyser tedf) {
		electrolyser = tedf;

		//Battery
		this.addSlotToContainer(new Slot(tedf, 0, 186, 109));
		//Upgrades
		this.addSlotToContainer(new Slot(tedf, 1, 186, 140));
		this.addSlotToContainer(new Slot(tedf, 2, 186, 158));
		//Input
		this.addSlotToContainer(new Slot(tedf, 14, 10, 22));
		//Outputs
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 15, 136, 18));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 16, 154, 18));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 17, 136, 36));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 18, 154, 36));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 19, 136, 54));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 20, 154, 54));

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
