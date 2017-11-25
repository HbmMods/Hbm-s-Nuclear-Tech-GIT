package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerAMSLimiter;
import com.hbm.inventory.container.ContainerMachinePress;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIAMSLimiter extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_ams_limiter.png");
	private TileEntityAMSLimiter limiter;
	
	public GUIAMSLimiter(InventoryPlayer invPlayer, TileEntityAMSLimiter tedf) {
		super(new ContainerAMSLimiter(invPlayer, tedf));
		limiter = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		limiter.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 69 - 52, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 69 - 52, 16, 52, limiter.power, limiter.maxPower);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 69 - 52, 16, 52, new String[] { "Efficiency:", limiter.efficiency + "%" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 69 - 52, 16, 52, new String[] { "Heat:", limiter.heat + "/" + limiter.maxHeat });
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.limiter.hasCustomInventoryName() ? this.limiter.getInventoryName() : I18n.format(this.limiter.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int) limiter.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 134, guiTop + 69 - i, 192, 52 - i, 16, i);
		
		int j = limiter.getEfficiencyScaled(52);
		drawTexturedModalRect(guiLeft + 152, guiTop + 69 - j, 208, 52 - j, 16, j);
		
		int k = limiter.getHeatScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 69 - k, 176, 52 - k, 16, k);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(limiter.tank.getSheet());

		limiter.tank.renderTank(this, guiLeft + 26, guiTop + 69, limiter.tank.getTankType().textureX() * FluidTank.x, limiter.tank.getTankType().textureY() * FluidTank.y, 16, 52);
	}
}
