package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerElectricFurnace;
import com.hbm.inventory.container.ContainerMachineFluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineElectricFurnace;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineFluidTank extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_tank.png");
	private TileEntityMachineFluidTank tank;

	public GUIMachineFluidTank(InventoryPlayer invPlayer, TileEntityMachineFluidTank tedf) {
		super(new ContainerMachineFluidTank(invPlayer, tedf));
		tank = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		tank.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 71, guiTop + 69 - 52, 34, 52);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.tank.hasCustomInventoryName() ? this.tank.getInventoryName() : I18n.format(this.tank.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(tank.dna())
			drawTexturedModalRect(guiLeft + 152, guiTop + 53, 176, 0, 16, 16);

		Minecraft.getMinecraft().getTextureManager().bindTexture(FluidTank.fluidTextures);

		tank.tank.renderTank(this, guiLeft + 71, guiTop + 69, tank.tank.getTankType().textureX() * FluidTank.x, tank.tank.getTankType().textureY() * FluidTank.y, 16, 52);
		tank.tank.renderTank(this, guiLeft + 71 + 16, guiTop + 69, tank.tank.getTankType().textureX() * FluidTank.x, tank.tank.getTankType().textureY() * FluidTank.y, 16, 52);
		tank.tank.renderTank(this, guiLeft + 71 + 32, guiTop + 69, tank.tank.getTankType().textureX() * FluidTank.x, tank.tank.getTankType().textureY() * FluidTank.y, 2, 52);
	}
}
