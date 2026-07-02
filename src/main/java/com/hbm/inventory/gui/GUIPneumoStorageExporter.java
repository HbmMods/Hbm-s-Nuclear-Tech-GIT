package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPneumoStorageExporter;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageExporter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIPneumoStorageExporter extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_pneumatic_exporter.png");
	private TileEntityPneumoStorageExporter importer;

	public GUIPneumoStorageExporter(InventoryPlayer invPlayer, TileEntityPneumoStorageExporter importer) {
		super(new ContainerPneumoStorageExporter(invPlayer, importer));
		this.importer = importer;
		
		this.xSize = 176;
		this.ySize = 185;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		clickSendFlag(importer, x, y, 142, 16, 18, 18, "continuous");
		clickSendFlag(importer, x, y, 142, 34, 18, 18, "request");
		clickSendFlag(importer, x, y, 142, 52, 18, 18, "ror");
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.importer.hasCustomInventoryName() ? this.importer.getInventoryName() : I18n.format(this.importer.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 5, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(!this.importer.continuousRequest) drawTexturedModalRect(guiLeft + 142, guiTop + 16, xSize, 0, 18, 18);
		if(!this.importer.rorConfiguredMode) drawTexturedModalRect(guiLeft + 142, guiTop + 52, xSize, 18, 18, 18);
		if(this.importer.requestMode == importer.MODE_FULL_STACK) drawTexturedModalRect(guiLeft + 142, guiTop + 34, xSize + 18, 0, 18, 18);
		if(this.importer.requestMode == importer.MODE_FULL_REQUEST) drawTexturedModalRect(guiLeft + 142, guiTop + 34, xSize + 18, 18, 18, 18);
	}
}
