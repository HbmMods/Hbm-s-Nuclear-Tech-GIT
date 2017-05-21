package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.inventory.container.ContainerMachineRTG;
import com.hbm.inventory.container.ContainerUF6Tank;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityMachineOilWell;
import com.hbm.tileentity.TileEntityMachineRTG;
import com.hbm.tileentity.TileEntityMachineUF6Tank;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineOilWell extends GuiContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_well.png");
	private TileEntityMachineOilWell derrick;
	
	public GUIMachineOilWell(InventoryPlayer invPlayer, TileEntityMachineOilWell tedf) {
		super(new ContainerMachineOilWell(invPlayer, tedf));
		derrick = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
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
		
		int i = derrick.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 69 - i, 176, 52 - i, 16, i);
		
		int j = derrick.getOilScaled(52);
		drawTexturedModalRect(guiLeft + 80, guiTop + 69 - j, 192, 52 - j, 34, j);
		
		int k = derrick.warning;
		if(k == 2)
			drawTexturedModalRect(guiLeft + 44, guiTop + 17, 176, 52, 16, 16);
		if(k == 1)
			drawTexturedModalRect(guiLeft + 44, guiTop + 17, 192, 52, 16, 16);
	}
}
