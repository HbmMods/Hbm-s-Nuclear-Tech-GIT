package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerWatz;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityWatz;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIWatz extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_watz.png");
	private TileEntityWatz watz;

	public GUIWatz(InventoryPlayer invPlayer, TileEntityWatz watz) {
		super(new ContainerWatz(invPlayer, watz));
		this.watz = watz;
		
		this.xSize = 176;
		this.ySize = 229;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		this.drawCustomInfoStat(x, y, guiLeft + 13, guiTop + 100, 18, 18, x, y, new String[] { String.format("%,d", watz.heat) + " TU" });
		this.drawCustomInfoStat(x, y, guiLeft + 143, guiTop + 71, 16, 16, x, y, new String[] { watz.isLocked ? "Unlock pellet IO configuration" : "Lock pellet IO configuration" });

		watz.tanks[0].renderTankInfo(this, x, y, guiLeft + 142, guiTop + 23, 6, 45);
		watz.tanks[1].renderTankInfo(this, x, y, guiLeft + 148, guiTop + 23, 6, 45);
		watz.tanks[2].renderTankInfo(this, x, y, guiLeft + 154, guiTop + 23, 6, 45);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 93, 4210752);
		
		String flux = "" + ((int)(watz.fluxDisplay * 10)) / 10.0D;
		this.fontRendererObj.drawString(flux, 159 - this.fontRendererObj.getStringWidth(flux), 105, 0x00ff00);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//float col = (float) (Math.sin(System.currentTimeMillis() / 500D) * 0.5 + 0.5);
		//GL11.glColor4f(1.0F, col, col, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 131, 122);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		drawTexturedModalRect(guiLeft + 131, guiTop, 131, 0, 36, 122);
		drawTexturedModalRect(guiLeft, guiTop + 130, 0, 130, xSize, 99);
		drawTexturedModalRect(guiLeft + 126, guiTop + 31, 176, 31, 9, 60);
		drawTexturedModalRect(guiLeft + 105, guiTop + 96, 185, 26, 30, 26);
		drawTexturedModalRect(guiLeft + 9, guiTop + 96, 184, 0, 26, 26);

		watz.tanks[0].renderTank(guiLeft + 143, guiTop + 69, this.zLevel, 4, 43);
		watz.tanks[1].renderTank(guiLeft + 149, guiTop + 69, this.zLevel, 4, 43);
		watz.tanks[2].renderTank(guiLeft + 155, guiTop + 69, this.zLevel, 4, 43);
	}
}
