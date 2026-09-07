package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerLaunchpadLambda extends ContainerBase {
	
	public ContainerLaunchpadLambda(InventoryPlayer invPlayer, IInventory tedf) {
		super(invPlayer, tedf);
		
		//Lambda
		this.addSlotToContainer(new SlotNonRetarded(tedf, 0, 35, 17));
		//Satellite
		this.addSlotToContainer(new SlotNonRetarded(tedf, 1, 53, 17));
		//Kerosene IN
		this.addSlotToContainer(new Slot(tedf, 2, 107, 80));
		//Kerosene OUT
		this.addSlotToContainer(new Slot(tedf, 3, 107, 98));
		//Oxyden IN
		this.addSlotToContainer(new Slot(tedf, 4, 125, 80));
		//Oxyden OUT
		this.addSlotToContainer(new Slot(tedf, 5, 125, 98));
		//Battery
		this.addSlotToContainer(new Slot(tedf, 6, 89, 80));
		
		this.playerInv(invPlayer, 144);
	}
}
