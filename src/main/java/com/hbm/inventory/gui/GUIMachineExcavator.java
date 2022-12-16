package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerMachineExcavator;
import com.hbm.tileentity.machine.TileEntityMachineExcavator;

import net.minecraft.entity.player.InventoryPlayer;

public class GUIMachineExcavator extends GuiInfoContainer {

	public GUIMachineExcavator(InventoryPlayer inventory, TileEntityMachineExcavator tile) {
		super(new ContainerMachineExcavator(inventory, tile));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		
	}
}
