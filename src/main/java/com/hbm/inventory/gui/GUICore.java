package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCore;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICore extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/dfc/gui_core.png");
	private TileEntityCore core;
	
	public GUICore(InventoryPlayer invPlayer, TileEntityCore tedf) {
		super(new ContainerCore(invPlayer, tedf));
		core = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		core.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 17, 16, 52);
		core.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 17, 16, 52);

		String[] text = new String[] { "Restriction Field: " + core.field + "%" };
		String[] text1 = new String[] { "Heat Saturation: " + core.heat + "%" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 8, guiTop + 17, 16, 52, mouseX, mouseY, text);
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 152, guiTop + 17, 16, 52, mouseX, mouseY, text1);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		
		String name = this.core.hasCustomInventoryName() ? this.core.getInventoryName() : I18n.format(this.core.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = core.getFieldScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 69 - i, 176, 52 - i, 16, i);
		
		int j = core.getHeatScaled(52);
		drawTexturedModalRect(guiLeft + 152, guiTop + 69 - j, 192, 52 - j, 16, j);

		core.tanks[0].renderTank(guiLeft + 26, guiTop + 69, this.zLevel, 16, 52);
		core.tanks[1].renderTank(guiLeft + 134, guiTop + 69, this.zLevel, 16, 52);
	}
}
