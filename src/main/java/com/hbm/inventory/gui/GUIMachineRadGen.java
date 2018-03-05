package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineRadGen;
import com.hbm.inventory.container.ContainerMachineSiren;
import com.hbm.items.tool.ItemCassette.TrackType;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineRadGen;
import com.hbm.tileentity.machine.TileEntityMachineSiren;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRadGen extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_radgen.png");
	private TileEntityMachineRadGen radgen;

	public GUIMachineRadGen(InventoryPlayer invPlayer, TileEntityMachineRadGen tedf) {
		super(new ContainerMachineRadGen(invPlayer, tedf));
		radgen = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 35, guiTop + 69 - 52, 16, 52, new String[] { "Fuel: " + radgen.getFuelScaled(100) + "%" });
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 143, guiTop + 69 - 52, 16, 52, radgen.power, radgen.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.radgen.hasCustomInventoryName() ? this.radgen.getInventoryName() : I18n.format(this.radgen.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int)radgen.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 143, guiTop + 69 - i, 16, 218 - i, 16, i);
		
		int j = radgen.getFuelScaled(52);
		drawTexturedModalRect(guiLeft + 35, guiTop + 69 - j, 0, 218 - j, 16, j);
		
		int k = radgen.mode;
		if(k == 1)
			drawTexturedModalRect(guiLeft + 106, guiTop + 16, 32, 166, 18, 18);
		if(k == 2)
			drawTexturedModalRect(guiLeft + 106, guiTop + 16, 32, 184, 18, 18);
		
		int l = radgen.getStrengthScaled(12);
		int sx = 140;
		int sy = 166;
		if(l > 0 && l < 7) {
			sx = 176;
			sy = (l - 1) * 36;
		}
		if(l > 6) {
			sx = 212;
			sy = (l - 7) * 36;
		}
		drawTexturedModalRect(guiLeft + 70, guiTop + 25, sx, sy, 36, 36);
	}
}
