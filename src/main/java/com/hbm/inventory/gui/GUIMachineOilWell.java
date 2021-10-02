package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityMachineOilWell;
import com.hbm.tileentity.machine.oil.TileEntityOilDrillBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineOilWell extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_well.png");
	private TileEntityOilDrillBase derrick;
	
	public GUIMachineOilWell(InventoryPlayer invPlayer, TileEntityOilDrillBase tedf) {
		super(new ContainerMachineOilWell(invPlayer, tedf));
		derrick = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		derrick.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 16, 52);
		derrick.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 69 - 52, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 17, 16, 34, derrick.power, derrick.getMaxPower());
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.derrick.hasCustomInventoryName() ? this.derrick.getInventoryName() : I18n.format(this.derrick.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int)(derrick.getPower() * 34 / derrick.getMaxPower());
		drawTexturedModalRect(guiLeft + 8, guiTop + 51 - i, 176, 34 - i, 16, i);
		
		int k = derrick.indicator;
		
		if(k != 0)
			drawTexturedModalRect(guiLeft + 35, guiTop + 17, 176 + (k - 1) * 16, 52, 16, 16);
		
		if(derrick.tanks.length < 3) {
			drawTexturedModalRect(guiLeft + 34, guiTop + 36, 192, 0, 18, 34);
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(derrick.tanks[0].getSheet());
		derrick.tanks[0].renderTank(this, guiLeft + 62, guiTop + 69, derrick.tanks[0].getTankType().textureX() * FluidTank.x, derrick.tanks[0].getTankType().textureY() * FluidTank.y, 16, 52);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(derrick.tanks[1].getSheet());
		derrick.tanks[1].renderTank(this, guiLeft + 116, guiTop + 69, derrick.tanks[1].getTankType().textureX() * FluidTank.x, derrick.tanks[1].getTankType().textureY() * FluidTank.y, 16, 52);
	}
}
