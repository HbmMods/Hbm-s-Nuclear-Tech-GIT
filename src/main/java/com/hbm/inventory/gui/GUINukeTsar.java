package com.hbm.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeTsar;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityNukeTsar;
import com.hbm.util.I18nUtil;

public class GUINukeTsar extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/tsarBombaSchematic.png");
	private TileEntityNukeTsar testNuke;
	
	public GUINukeTsar(InventoryPlayer invPlayer, TileEntityNukeTsar tedf) {
		super(new ContainerNukeTsar(invPlayer, tedf));
		testNuke = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] descText = I18nUtil.resolveKeyArray("desc.gui.nukeTsar.desc");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 16, 16, 16, guiLeft - 8, guiTop + 16 + 16, descText);
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

		if(testNuke.isReady())
		{
			drawTexturedModalRect(guiLeft + 133, guiTop + 34, 176, 0, 16, 16);
		}

		if(testNuke.isFilled())
		{
			drawTexturedModalRect(guiLeft + 133, guiTop + 34, 176, 18, 16, 16);
		}
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 16, 16, 16, 2);
	}
}
