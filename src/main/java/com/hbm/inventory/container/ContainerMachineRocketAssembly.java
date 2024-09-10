package com.hbm.inventory.container;

import com.hbm.handler.RocketStruct;
import com.hbm.inventory.SlotRocket;
import com.hbm.inventory.SlotRocket.SlotCapsule;
import com.hbm.inventory.SlotRocket.SlotDrive;
import com.hbm.inventory.SlotRocket.SlotRocketPart;
import com.hbm.items.weapon.ItemCustomMissilePart.PartType;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerMachineRocketAssembly extends ContainerBase {

	public ContainerMachineRocketAssembly(InventoryPlayer invPlayer, IInventory machine) {
		super(invPlayer, machine);

		int slotId = 0;

		// Capsule slot
		addSlotToContainer(new SlotCapsule(machine, slotId++, 18, 13));

		// Stages
		for(int i = 0; i < RocketStruct.MAX_STAGES; i++) {
			addSlotToContainer(new SlotRocketPart(machine, slotId++, 18, 44, i, PartType.FUSELAGE));
			addSlotToContainer(new SlotRocketPart(machine, slotId++, 18, 62, i, PartType.FINS));
			addSlotToContainer(new SlotRocketPart(machine, slotId++, 18, 80, i, PartType.THRUSTER));
		}

		// Result
		addSlotToContainer(new SlotRocket(machine, slotId++, 42, 91));

		// Drives
		addSlotToContainer(new SlotDrive(machine, slotId++, 165, 54));
		addSlotToContainer(new SlotDrive(machine, slotId++, 165, 87));

		addSlots(invPlayer, 9, 8, 142, 3, 9); // Player inventory
		addSlots(invPlayer, 0, 8, 200, 1, 9); // Player hotbar
	}

}
