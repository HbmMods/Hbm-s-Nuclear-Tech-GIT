package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerHeaterHeatex;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityHeaterHeatex;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIHeaterHeatex extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_heatex.png");
	private TileEntityHeaterHeatex heater;

	public GUIHeaterHeatex(InventoryPlayer invPlayer, TileEntityHeaterHeatex tedf) {
		super(new ContainerHeaterHeatex(invPlayer, tedf));
		heater = tedf;
		
		this.xSize = 176;
		this.ySize = 203;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		
	}
}
