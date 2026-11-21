package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.tileentity.machine.TileEntityMachineTrackingRadar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerTrackingRadar extends ContainerBase {
	
	protected TileEntityMachineTrackingRadar tile;

	public ContainerTrackingRadar(InventoryPlayer invPlayer, TileEntityMachineTrackingRadar tedf) {
		super(invPlayer, tedf);
		this.tile = tedf;

		// Battery
		this.addSlotToContainer(new SlotNonRetarded(tedf, 0, 232, 108));
		
		this.playerInv(invPlayer, 48, 158);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}
}
