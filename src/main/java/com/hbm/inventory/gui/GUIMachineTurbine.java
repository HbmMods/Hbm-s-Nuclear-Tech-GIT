package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineTurbine;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineTurbine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineTurbine extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_turbine.png");
	private TileEntityMachineTurbine diFurnace;
	
	public GUIMachineTurbine(InventoryPlayer invPlayer, TileEntityMachineTurbine tedf) {
		super(new ContainerMachineTurbine(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 16, 52);
		diFurnace.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 69 - 52, 16, 52);
		
		if(diFurnace.tanks[1].getTankType() == Fluids.NONE) {
			
			String[] text2 = new String[] { "Error: Invalid fluid!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32, 16, 16, guiLeft - 8, guiTop + 36 + 16 + 32, text2);
		}
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 123, guiTop + 69 - 34, 7, 34, diFurnace.power, diFurnace.maxPower);
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

		if(diFurnace.tanks[0].getTankType() == Fluids.STEAM) drawTexturedModalRect(guiLeft + 99, guiTop + 18, 183, 0, 14, 14);
		if(diFurnace.tanks[0].getTankType() == Fluids.HOTSTEAM) drawTexturedModalRect(guiLeft + 99, guiTop + 18, 183, 14, 14, 14);
		if(diFurnace.tanks[0].getTankType() == Fluids.SUPERHOTSTEAM) drawTexturedModalRect(guiLeft + 99, guiTop + 18, 183, 28, 14, 14);
		if(diFurnace.tanks[0].getTankType() == Fluids.ULTRAHOTSTEAM) drawTexturedModalRect(guiLeft + 99, guiTop + 18, 183, 42, 14, 14);
		if(diFurnace.tanks[0].getTankType() == Fluids.CRYOGEL_MOD_HOT) drawTexturedModalRect(guiLeft + 99, guiTop + 18, 183, 56, 14, 14);

		int i = (int)diFurnace.getPowerScaled(34);
		drawTexturedModalRect(guiLeft + 123, guiTop + 69 - i, 176, 34 - i, 7, i);
		
		if(diFurnace.tanks[1].getTankType() == Fluids.NONE) {
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);
		}
		
		diFurnace.tanks[0].renderTank(guiLeft + 62, guiTop + 69, this.zLevel, 16, 52);
		diFurnace.tanks[1].renderTank(guiLeft + 134, guiTop + 69, this.zLevel, 16, 52);
	}
}
