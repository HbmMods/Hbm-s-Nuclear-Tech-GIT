package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerMachineBoiler;
import com.hbm.inventory.container.ContainerMachineGasCent;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineBoiler;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineBoiler extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_boiler.png");
	private TileEntityMachineBoiler diFurnace;
	
	public GUIMachineBoiler(InventoryPlayer invPlayer, TileEntityMachineBoiler tedf) {
		super(new ContainerMachineBoiler(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 16, 52);
		diFurnace.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 69 - 52, 16, 52);
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 102, guiTop + 16, 8, 18, mouseX, mouseY, new String[] { String.valueOf((int)((double)diFurnace.heat / 100D)) + "°C"});
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

		/*int i = (int)diFurnace.getPowerRemainingScaled(34);
		drawTexturedModalRect(guiLeft + 8, guiTop + 51 - i, 176, 34 - i, 16, i);*/

		int j = (int)diFurnace.getHeatScaled(17);
		drawTexturedModalRect(guiLeft + 103, guiTop + 33 - j, 194, 16 - j, 6, j);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(diFurnace.tanks[0].getSheet());
		diFurnace.tanks[0].renderTank(this, guiLeft + 62, guiTop + 69, diFurnace.tanks[0].getTankType().textureX() * FluidTank.x, diFurnace.tanks[0].getTankType().textureY() * FluidTank.y, 16, 52);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(diFurnace.tanks[1].getSheet());
		diFurnace.tanks[1].renderTank(this, guiLeft + 134, guiTop + 69, diFurnace.tanks[1].getTankType().textureX() * FluidTank.x, diFurnace.tanks[1].getTankType().textureY() * FluidTank.y, 16, 52);
	}
}
