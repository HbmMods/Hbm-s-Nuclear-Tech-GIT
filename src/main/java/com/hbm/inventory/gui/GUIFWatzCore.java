package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerFWatzCore;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFWatzCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIFWatzCore extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_fwatz_multiblock.png");
	private TileEntityFWatzCore diFurnace;

	public GUIFWatzCore(InventoryPlayer invPlayer, TileEntityFWatzCore tedf) {
		super(new ContainerFWatzCore(invPlayer, tedf));
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
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 106 - 88, 16, 88, diFurnace.power, diFurnace.maxPower);
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

		int k = (int)diFurnace.getPowerScaled(88);
		drawTexturedModalRect(guiLeft + 26, guiTop + 106 - k, 192, 88 - k, 16, k);
		
		if(diFurnace.isRunning())
			drawTexturedModalRect(guiLeft + 64, guiTop + 29, 176, 88, 48, 48);
		
		int m = diFurnace.getSingularityType();
		drawTexturedModalRect(guiLeft + 98, guiTop + 109, 240, 4 * m, 16, 4);
		
		diFurnace.tanks[0].renderTank(guiLeft + 8, guiTop + 88, this.zLevel, 16, 70);
		diFurnace.tanks[1].renderTank(guiLeft + 134, guiTop + 88, this.zLevel, 16, 70);
		diFurnace.tanks[2].renderTank(guiLeft + 152, guiTop + 88, this.zLevel, 16, 70);
	}
}
