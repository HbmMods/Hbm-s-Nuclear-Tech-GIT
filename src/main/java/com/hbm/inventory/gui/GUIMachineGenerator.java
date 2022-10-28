package com.hbm.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerGenerator;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineGenerator;

public class GUIMachineGenerator extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_generator.png");
	private TileEntityMachineGenerator diFurnace;

	public GUIMachineGenerator(InventoryPlayer invPlayer, TileEntityMachineGenerator tedf) {
		super(new ContainerGenerator(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 88 - 52, 16, 52);
		diFurnace.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 88 - 52, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 88 - 52, 16, 52, diFurnace.power, diFurnace.powerMax);

		if(diFurnace.tanks[0].getFill() <= 0) {
			String[] text = new String[] { "Error: Water is required for",
					"the reactor to function properly!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		}

		if(diFurnace.tanks[1].getFill() <= 0) {
			String[] text1 = new String[] { "Use of coolant is advised." };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
		}
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
		
		if(diFurnace.hasPower()) {
			int i = (int)diFurnace.getPowerScaled(52);
			drawTexturedModalRect(guiLeft + 62, guiTop + 88 - i, 224, 52 - i, 16, i);
		}
		
		if(diFurnace.hasHeat()) {
			int i = diFurnace.getHeatScaled(52);
			drawTexturedModalRect(guiLeft + 98, guiTop + 88 - i, 208, 52 - i, 16, i);
		}
		
		if(diFurnace.tanks[0].getFill() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 6);
		
		if(diFurnace.tanks[1].getFill() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 7);
		
		diFurnace.tanks[0].renderTank(guiLeft + 8, guiTop + 88, this.zLevel, 16, 52);
		diFurnace.tanks[1].renderTank(guiLeft + 26, guiTop + 88, this.zLevel, 16, 52);
	}
}
