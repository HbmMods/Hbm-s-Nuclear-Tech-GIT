package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerICF;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityICF;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIICF extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_icf.png");
	private TileEntityICF icf;

	public GUIICF(InventoryPlayer invPlayer, TileEntityICF icf) {
		super(new ContainerICF(invPlayer, icf));
		this.icf = icf;
		
		this.xSize = 248;
		this.ySize = 222;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.icf.hasCustomInventoryName() ? this.icf.getInventoryName() : I18n.format(this.icf.getInventoryName());
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 93, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
