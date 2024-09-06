package com.hbm.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerOrbitalStationComputer extends Container {

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
}
