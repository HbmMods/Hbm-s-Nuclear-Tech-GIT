package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerFusionTorus;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.GaugeUtil;
import com.hbm.tileentity.machine.fusion.TileEntityFusionTorus;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIFusionTorus extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_fusion_torus.png");
	private TileEntityFusionTorus torus;

	public GUIFusionTorus(InventoryPlayer invPlayer, TileEntityFusionTorus torus) {
		super(new ContainerFusionTorus(invPlayer, torus));
		this.torus = torus;
		
		this.xSize = 230;
		this.ySize = 244;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		torus.tanks[0].renderTankInfo(this, x, y, guiLeft + 188, guiTop + 46, 16, 52);
		torus.tanks[1].renderTankInfo(this, x, y, guiLeft + 206, guiTop + 46, 16, 52);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.torus.hasCustomInventoryName() ? this.torus.getInventoryName() : I18n.format(this.torus.getInventoryName());
		this.fontRendererObj.drawString(name, 106 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 35, this.ySize - 93, 4210752);

		this.fontRendererObj.drawString(EnumChatFormatting.AQUA + "/123K", 136 + 54, 32, 4210752);
		int heat = (int) Math.ceil(300);
		String label = (heat > 123 ? EnumChatFormatting.RED : EnumChatFormatting.AQUA) + "" + heat + "K";
		this.fontRendererObj.drawString(label, 166 + 54 - this.fontRendererObj.getStringWidth(label), 22, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		// power LED
		drawTexturedModalRect(guiLeft + 160, guiTop + 115, 246, 14, 8, 8);
		// coolant LED
		drawTexturedModalRect(guiLeft + 170, guiTop + 115, 246, 14, 8, 8);
		// plasma LED
		drawTexturedModalRect(guiLeft + 180, guiTop + 115, 246, 14, 8, 8);

		// recipe LED
		drawTexturedModalRect(guiLeft + 87, guiTop + 76, 249, 0, 3, 6);
		// progress LED
		drawTexturedModalRect(guiLeft + 92, guiTop + 76, 249, 0, 3, 6);
		
		double gauge = BobMathUtil.sps((Minecraft.getMinecraft().theWorld.getTotalWorldTime() + interp) * 0.25) / 2 + 0.5D;
		
		// input energy
		GaugeUtil.drawSmoothGauge(guiLeft + 52, guiTop + 124, this.zLevel, gauge, 5, 2, 1, 0xA00000);
		// output genergy
		GaugeUtil.drawSmoothGauge(guiLeft + 88, guiTop + 124, this.zLevel, gauge, 5, 2, 1, 0xA00000);
		// fuel consumption
		GaugeUtil.drawSmoothGauge(guiLeft + 124, guiTop + 124, this.zLevel, gauge, 5, 2, 1, 0xA00000);

		// coolant
		torus.tanks[0].renderTank(guiLeft + 188, guiTop + 98, this.zLevel, 16, 52);
		torus.tanks[1].renderTank(guiLeft + 206, guiTop + 98, this.zLevel, 16, 52);
	}
}
