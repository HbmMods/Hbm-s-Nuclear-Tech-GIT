package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPneumoStorageExporter;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageExporter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
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
		
		this.drawCustomInfoStat(x, y, guiLeft + 142, guiTop + 16, 18, 18, x, y, "Request mode: " + EnumChatFormatting.YELLOW + (this.importer.continuousRequest ? "Continuous" : "By request"));
		
		this.drawCustomInfoStat(x, y, guiLeft + 142, guiTop + 34, 18, 18, x, y, "Request type: " + EnumChatFormatting.YELLOW + (
				this.importer.requestMode == this.importer.MODE_AS_MUCH_AS_POSSIBLE ? "As much as possible" : 
				this.importer.requestMode == this.importer.MODE_FULL_STACK ? "Only full stacks" : "Only full requests"
		));
		
		if(this.importer.rorConfiguredMode) {
			String[] label = new String[10];
			label[0] = "Filter type: " + EnumChatFormatting.YELLOW + "RoR configured";
			for(int i = 0; i < 9; i++) {
				boolean hasFilter = this.importer.rorFilters[i][0] != 0 && this.importer.rorFilters[i][2] > 0;
				label[i + 1] = "Slot " + (i + 1) + ": " + (!hasFilter ? "None" : ("Item #" + this.importer.rorFilters[i][0] + " with Meta " + this.importer.rorFilters[i][1] + " x" + this.importer.rorFilters[i][2]));
			}
			this.drawCustomInfoStat(x, y, guiLeft + 142, guiTop + 52, 18, 18, x, y, label);
		} else {
			this.drawCustomInfoStat(x, y, guiLeft + 142, guiTop + 52, 18, 18, x, y, "Filter type: " + EnumChatFormatting.YELLOW + "Manually configured");
		}
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

		if(this.importer.rorConfiguredMode) {
			drawTexturedModalRect(guiLeft + 142, guiTop + 52, xSize, 18, 18, 18);
			drawTexturedModalRect(guiLeft + 14, guiTop + 14, 77, 14, 58, 58);
		}
		if(!this.importer.continuousRequest) drawTexturedModalRect(guiLeft + 142, guiTop + 16, xSize, 0, 18, 18);
		if(this.importer.requestMode == importer.MODE_FULL_STACK) drawTexturedModalRect(guiLeft + 142, guiTop + 34, xSize + 18, 0, 18, 18);
		if(this.importer.requestMode == importer.MODE_FULL_REQUEST) drawTexturedModalRect(guiLeft + 142, guiTop + 34, xSize + 18, 18, 18, 18);
	}
}
