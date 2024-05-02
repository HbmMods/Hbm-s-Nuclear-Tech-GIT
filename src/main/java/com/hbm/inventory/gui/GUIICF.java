package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerICF;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.GaugeUtil;
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
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		icf.tanks[0].renderTankInfo(this, x, y, guiLeft + 44, guiTop + 18, 16, 70);
		icf.tanks[1].renderTankInfo(this, x, y, guiLeft + 188, guiTop + 18, 16, 70);
		icf.tanks[2].renderTankInfo(this, x, y, guiLeft + 224, guiTop + 18, 16, 70);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.icf.hasCustomInventoryName() ? this.icf.getInventoryName() : I18n.format(this.icf.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 44, this.ySize - 93, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		GaugeUtil.drawSmoothGauge(guiLeft + 196, guiTop + 98, this.zLevel, 0D, 5, 2, 1, 0xFF00AF);

		icf.tanks[0].renderTank(guiLeft + 44, guiTop + 88, this.zLevel, 16, 70);
		icf.tanks[1].renderTank(guiLeft + 188, guiTop + 88, this.zLevel, 16, 70);
		icf.tanks[2].renderTank(guiLeft + 224, guiTop + 88, this.zLevel, 16, 70);
	}
}
