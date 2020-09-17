package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeMike;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityNukeMike;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINukeMike extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/ivyMikeSchematic.png");
	private TileEntityNukeMike testNuke;
	
	public GUINukeMike(InventoryPlayer invPlayer, TileEntityNukeMike tedf) {
		super(new ContainerNukeMike(invPlayer, tedf));
		testNuke = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.testNuke.hasCustomInventoryName() ? this.testNuke.getInventoryName() : I18n.format(this.testNuke.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(testNuke.isReady() && !testNuke.isFilled())
		{
			drawTexturedModalRect(guiLeft + 142, guiTop + 34, 176, 0, 18, 18);
		}

		if(testNuke.isReady() && testNuke.isFilled())
		{
			drawTexturedModalRect(guiLeft + 142, guiTop + 34, 176, 18, 18, 18);
		}
	}

}
