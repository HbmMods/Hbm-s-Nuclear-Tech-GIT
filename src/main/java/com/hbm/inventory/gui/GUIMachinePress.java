package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachinePress;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachinePress extends GuiContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_press.png");
	private TileEntityMachinePress assembler;
	
	public GUIMachinePress(InventoryPlayer invPlayer, TileEntityMachinePress tedf) {
		super(new ContainerMachinePress(invPlayer, tedf));
		assembler = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.assembler.hasCustomInventoryName() ? this.assembler.getInventoryName() : I18n.format(this.assembler.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = assembler.getPowerScaled(12);
		drawTexturedModalRect(guiLeft + 25, guiTop + 16, 176, 14 + 18 * i, 18, 18);
		
		int l = assembler.getBurnScaled(13);
        this.drawTexturedModalRect(guiLeft + 27, guiTop + 49 - l, 176, 13 - l, 13, l);
		
		int k = assembler.getProgressScaled(16);
        this.drawTexturedModalRect(guiLeft + 79, guiTop + 35, 194, 0, 18, k);
	}
}
