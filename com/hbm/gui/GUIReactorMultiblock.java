package com.hbm.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.TileEntityMachineGenerator;
import com.hbm.blocks.TileEntityReactorMultiblock;
import com.hbm.blocks.TileEntityRtgFurnace;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GUIReactorMultiblock extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_multiblock.png");
	private TileEntityReactorMultiblock diFurnace;

	public GUIReactorMultiblock(InventoryPlayer invPlayer, TileEntityReactorMultiblock tedf) {
		super(new ContainerReactorMultiblock(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
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

		int i = diFurnace.getWaterScaled(70);
		drawTexturedModalRect(guiLeft + 8, guiTop + 88 - i, 176, 70 - i, 16, i);

		int j = diFurnace.getCoolantScaled(70);
		drawTexturedModalRect(guiLeft + 26, guiTop + 88 - j, 192, 70 - j, 16, j);

		int k = diFurnace.getPowerScaled(70);
		drawTexturedModalRect(guiLeft + 44, guiTop + 88 - k, 208, 70 - k, 16, k);

		int l = diFurnace.getHeatScaled(124);
		drawTexturedModalRect(guiLeft + 26, guiTop + 108, 0, 222, l, 16);
	}
}
