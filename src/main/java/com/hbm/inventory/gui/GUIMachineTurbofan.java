package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineTurbofan;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.GaugeUtil;
import com.hbm.render.util.GaugeUtil.Gauge;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineTurbofan extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_turbofan.png");
	private TileEntityMachineTurbofan turbofan;

	public GUIMachineTurbofan(InventoryPlayer invPlayer, TileEntityMachineTurbofan tedf) {
		super(new ContainerMachineTurbofan(invPlayer, tedf));
		turbofan = tedf;
		
		this.xSize = 176;
		this.ySize = 203;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		//diFurnace.tank[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 53, guiTop + 69 - 52, 34, 52);
		//diFurnace.tank[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 130, guiTop + 69 - 52, 34, 52);
		//this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 157 - 9, guiTop + 69 - 52, 16, 52, diFurnace.power, diFurnace.maxPower);

		//String[] text = new String[] { "Acceptable upgrades:",
				//" -Pink (afterburner)" };
		//this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 96, guiTop + 21, 8, 8, guiLeft + 96, guiTop + 21 + 16, text);
		turbofan.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 35, guiTop + 17, 34, 52);
		if(turbofan.showBlood) turbofan.blood.renderTankInfo(this, mouseX, mouseY, guiLeft + 98, guiTop + 17, 16, 16);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 143, guiTop + 17, 16, 52, turbofan.power, turbofan.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.turbofan.hasCustomInventoryName() ? this.turbofan.getInventoryName() : I18n.format(this.turbofan.getInventoryName());
		
		this.fontRendererObj.drawString(name, 43 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		//if(diFurnace.power > 0) {
		//	int i = (int)diFurnace.getPowerScaled(52);
		//	drawTexturedModalRect(guiLeft + 161 - 9, guiTop + 69 - i, 176 + 16, 52 - i, 16, i);
		//}
		int i = (int)turbofan.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 152 - 9, guiTop + 69 - i, 176 + 16, 52 - i, 16, i);
		
		if(turbofan.afterburner > 0) {
			int a = Math.min(turbofan.afterburner, 6);
			drawTexturedModalRect(guiLeft + 98, guiTop + 44, 176, (a - 1) * 16, 16, 16);
		}

		//this.drawInfoPanel(guiLeft + 96, guiTop + 21, 8, 8, 8);
		
		//diFurnace.tank[0].renderTank(guiLeft + 53, guiTop + 69, this.zLevel, 34, 52);
		//diFurnace.tank[1].renderTank(guiLeft + 130, guiTop + 69, this.zLevel, 16, 52);
		if(turbofan.showBlood) GaugeUtil.renderGauge(Gauge.ROUND_SMALL, guiLeft + 97, guiTop + 16, this.zLevel, (double) turbofan.blood.getFill() / (double) turbofan.blood.getMaxFill());
		turbofan.tank.renderTank(guiLeft + 35, guiTop + 69, this.zLevel, 34, 52);
	}
}
