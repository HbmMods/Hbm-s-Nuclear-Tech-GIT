package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerIGenerator;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.GaugeUtil;
import com.hbm.render.util.GaugeUtil.Gauge;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIIGenerator extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_igen.png");
	private TileEntityMachineIGenerator diFurnace;

	public GUIIGenerator(InventoryPlayer invPlayer, TileEntityMachineIGenerator tedf) {
		super(new ContainerIGenerator(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 188;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int[] pellets = new int[] {0,1,0,1,0,0,0,1,2,0,0,2};
		
		for(int i = 0; i < pellets.length; i++) {
			
			drawTexturedModalRect(guiLeft + 6, guiTop + 106 - 4 * i, 188, 9 * pellets[i], 14, 9);
		}

		GaugeUtil.renderGauge(Gauge.BAR_SMALL, guiLeft + 76, guiTop + 20, this.zLevel, Math.sin(System.currentTimeMillis() * 0.0025D) * 0.5 + 0.5);
		GaugeUtil.renderGauge(Gauge.BAR_SMALL, guiLeft + 76, guiTop + 56, this.zLevel, Math.sin(System.currentTimeMillis() * 0.0025D) * 0.5 + 0.5);
		GaugeUtil.renderGauge(Gauge.BAR_SMALL, guiLeft + 76, guiTop + 92, this.zLevel, Math.sin(System.currentTimeMillis() * 0.0025D) * 0.5 + 0.5);

		GaugeUtil.renderGauge(Gauge.WIDE_SMALL, guiLeft + 148, guiTop + 26, this.zLevel, Math.sin(System.currentTimeMillis() * 0.0025D) * 0.5 + 0.5);
		GaugeUtil.renderGauge(Gauge.WIDE_SMALL, guiLeft + 148, guiTop + 62, this.zLevel, Math.sin(System.currentTimeMillis() * 0.0025D) * 0.5 + 0.5);
		GaugeUtil.renderGauge(Gauge.WIDE_SMALL, guiLeft + 148, guiTop + 98, this.zLevel, Math.sin(System.currentTimeMillis() * 0.0025D) * 0.5 + 0.5);
	}
}
