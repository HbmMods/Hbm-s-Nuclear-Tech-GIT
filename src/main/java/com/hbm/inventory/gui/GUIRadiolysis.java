package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerRadiolysis;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineRadiolysis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRadiolysis extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_radiolysis.png");
	private TileEntityMachineRadiolysis radiolysis;

	public GUIRadiolysis(InventoryPlayer invPlayer, TileEntityMachineRadiolysis tedf) {
		super(new ContainerRadiolysis(invPlayer, tedf));
		radiolysis = tedf;
		
		this.xSize = 230;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		radiolysis.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 61, guiTop + 17, 8, 52);
		radiolysis.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 87, guiTop + 17, 12, 16);
		radiolysis.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 87, guiTop + 53, 12, 16);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 17, 16, 34, radiolysis.power, radiolysis.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		
		String name = this.radiolysis.hasCustomInventoryName() ? this.radiolysis.getInventoryName() : I18n.format(this.radiolysis.getInventoryName());
		
		this.fontRendererObj.drawString(name, 88 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int)(radiolysis.getPower() * 34 / radiolysis.getMaxPower());
		drawTexturedModalRect(guiLeft + 8, guiTop + 51 - i, 240, 34 - i, 16, i);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(radiolysis.tanks[0].getSheet());
		radiolysis.tanks[0].renderTank(this, guiLeft + 61, guiTop + 69, radiolysis.tanks[0].getTankType().textureX() * FluidTank.x, radiolysis.tanks[0].getTankType().textureY() * FluidTank.y, 8, 52);
		
		for(byte j = 0; j < 2; j++) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(radiolysis.tanks[j].getSheet());
			radiolysis.tanks[j + 1].renderTank(this, guiLeft + 87, guiTop + 33 + j * 36, radiolysis.tanks[j + 1].getTankType().textureX() * FluidTank.x, radiolysis.tanks[j + 1].getTankType().textureY() * FluidTank.y, 12, 16);
		}
	}
}
