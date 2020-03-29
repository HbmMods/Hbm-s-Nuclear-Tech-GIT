package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerFusionMultiblock;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFusionMultiblock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIFusionMultiblock extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_fusion_multiblock.png");
	private TileEntityFusionMultiblock diFurnace;

	public GUIFusionMultiblock(InventoryPlayer invPlayer, TileEntityFusionMultiblock tedf) {
		super(new ContainerFusionMultiblock(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 88 - 70, 16, 70);
		diFurnace.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 88 - 70, 16, 70);
		diFurnace.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 88 - 70, 16, 70);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 88 - 70, 16, 70, diFurnace.power, diFurnace.maxPower);
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
		
		int k = (int)diFurnace.getPowerScaled(70);
		drawTexturedModalRect(guiLeft + 26, guiTop + 88 - k, 192, 88 - k, 16, k);
		
		if(diFurnace.isRunning())
			drawTexturedModalRect(guiLeft + 80, guiTop + 18, 240, 0, 16, 16);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(diFurnace.tanks[0].getSheet());
		diFurnace.tanks[0].renderTank(this, guiLeft + 8, guiTop + 88, diFurnace.tanks[0].getTankType().textureX() * FluidTank.x, diFurnace.tanks[0].getTankType().textureY() * FluidTank.y, 16, 70);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(diFurnace.tanks[1].getSheet());
		diFurnace.tanks[1].renderTank(this, guiLeft + 134, guiTop + 88, diFurnace.tanks[1].getTankType().textureX() * FluidTank.x, diFurnace.tanks[1].getTankType().textureY() * FluidTank.y, 16, 70);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(diFurnace.tanks[2].getSheet());
		diFurnace.tanks[2].renderTank(this, guiLeft + 152, guiTop + 88, diFurnace.tanks[2].getTankType().textureX() * FluidTank.x, diFurnace.tanks[2].getTankType().textureY() * FluidTank.y, 16, 70);
	}
}
