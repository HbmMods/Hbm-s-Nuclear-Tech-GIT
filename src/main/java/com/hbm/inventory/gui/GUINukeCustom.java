package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeCustom;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINukeCustom extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gunBombSchematic.png");
	private TileEntityNukeCustom testNuke;
	
	public GUINukeCustom(InventoryPlayer invPlayer, TileEntityNukeCustom tedf) {
		super(new ContainerNukeCustom(invPlayer, tedf));
		testNuke = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
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

		if(this.testNuke.tntStrength > 0)
		{
			drawTexturedModalRect(guiLeft + 16, guiTop + 89, 176, 0, 18, 18);
		}
		if(this.testNuke.nukeStrength > 0)
		{
			drawTexturedModalRect(guiLeft + 34, guiTop + 89, 176, 18, 18, 18);
		}
		if(this.testNuke.hydroStrength > 0)
		{
			drawTexturedModalRect(guiLeft + 52, guiTop + 89, 176, 36, 18, 18);
		}
		if(this.testNuke.amatStrength > 0)
		{
			drawTexturedModalRect(guiLeft + 70, guiTop + 89, 176, 54, 18, 18);
		}
		if(this.testNuke.dirtyStrength > 0)
		{
			drawTexturedModalRect(guiLeft + 88, guiTop + 89, 176, 72, 18, 18);
		}
		if(this.testNuke.schrabStrength > 0)
		{
			drawTexturedModalRect(guiLeft + 106, guiTop + 89, 176, 90, 18, 18);
		}
		if(this.testNuke.euphStrength > 0)
		{
			drawTexturedModalRect(guiLeft + 142, guiTop + 89, 176, 108, 18, 18);
		}
	}
}
