package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.tileentity.machine.TileEntityMachineBlastFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerBlastFurnace extends ContainerBase {
	
	protected TileEntityMachineBlastFurnace tile;

	public ContainerBlastFurnace(InventoryPlayer invPlayer, TileEntityMachineBlastFurnace tedf) {
		super(invPlayer, tedf);
		this.tile = tedf;

		// Fuel
		this.addSlotToContainer(new SlotNonRetarded(tedf, 0, 80, 81));
		// Input
		this.addSlotToContainer(new SlotNonRetarded(tedf, 1, 80, 27));
		this.addSlotToContainer(new SlotNonRetarded(tedf, 2, 80, 45));
		// Output
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 3, 134, 72));
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 4, 134, 90));
		
		this.playerInv(invPlayer, 140);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}
}
