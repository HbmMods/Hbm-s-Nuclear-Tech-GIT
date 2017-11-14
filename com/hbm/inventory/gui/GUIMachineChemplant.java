package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerMachineAssembler;
import com.hbm.inventory.container.ContainerMachineChemplant;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineChemplant extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_chemplant.png");
	private TileEntityMachineChemplant chemplant;
	
	public GUIMachineChemplant(InventoryPlayer invPlayer, TileEntityMachineChemplant tedf) {
		super(new ContainerMachineChemplant(invPlayer, tedf));
		chemplant = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		chemplant.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 52 - 34, 16, 34);
		chemplant.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 52 - 34, 16, 34);
		chemplant.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 52 - 34, 16, 34);
		chemplant.tanks[3].renderTankInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 52 - 34, 16, 34);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 44, guiTop + 70 - 52, 16, 52, chemplant.power, chemplant.maxPower);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.chemplant.hasCustomInventoryName() ? this.chemplant.getInventoryName() : I18n.format(this.chemplant.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int)chemplant.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 44, guiTop + 70 - i, 176, 52 - i, 16, i);

		int j = chemplant.getProgressScaled(90);
		drawTexturedModalRect(guiLeft + 43, guiTop + 89, 0, 222, j, 18);

		Minecraft.getMinecraft().getTextureManager().bindTexture(FluidTank.fluidTextures);

		chemplant.tanks[0].renderTank(this, guiLeft + 8, guiTop + 52, chemplant.tanks[0].getTankType().textureX() * FluidTank.x, chemplant.tanks[0].getTankType().textureY() * FluidTank.y, 16, 34);
		chemplant.tanks[1].renderTank(this, guiLeft + 26, guiTop + 52, chemplant.tanks[1].getTankType().textureX() * FluidTank.x, chemplant.tanks[1].getTankType().textureY() * FluidTank.y, 16, 34);
		chemplant.tanks[2].renderTank(this, guiLeft + 134, guiTop + 52, chemplant.tanks[2].getTankType().textureX() * FluidTank.x, chemplant.tanks[2].getTankType().textureY() * FluidTank.y, 16, 34);
		chemplant.tanks[3].renderTank(this, guiLeft + 152, guiTop + 52, chemplant.tanks[3].getTankType().textureX() * FluidTank.x, chemplant.tanks[3].getTankType().textureY() * FluidTank.y, 16, 34);
	}
}
