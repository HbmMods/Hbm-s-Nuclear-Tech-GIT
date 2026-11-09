package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.tileentity.machine.oil.TileEntityMachineSolidifier;

import net.minecraft.entity.player.InventoryPlayer;

public class ContainerSolidifier extends ContainerBase {
	
	public ContainerSolidifier(InventoryPlayer playerInv, TileEntityMachineSolidifier tile) {
		super(playerInv, tile);
		
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(playerInv.player, tile, 0, 71, 45));
		//Battery
		this.addSlotToContainer(new SlotNonRetarded(tile, 1, 134, 72));
		//Upgrades
		this.addSlotToContainer(new SlotNonRetarded(tile, 2, 98, 36));
		this.addSlotToContainer(new SlotNonRetarded(tile, 3, 98, 54));
		//ID
		this.addSlotToContainer(new SlotNonRetarded(tile, 4, 71, 72));

		this.playerInv(playerInv, 122);
	}
}
