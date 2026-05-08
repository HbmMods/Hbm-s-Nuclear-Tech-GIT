package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerMachineAssemblyFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

/**
 * GUIMachineAssemblyFactory - STUB IMPLEMENTATION for compilation purposes
 */
@SideOnly(Side.CLIENT)
public class GUIMachineAssemblyFactory extends GuiContainer {

	public GUIMachineAssemblyFactory(InventoryPlayer invPlayer, IInventory tile) {
		super(new ContainerMachineAssemblyFactory(invPlayer, tile));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	}
}
