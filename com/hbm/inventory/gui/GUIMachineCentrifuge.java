package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCentrifuge;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityMachineCentrifuge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCentrifuge extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/centrifuge.png");
	private TileEntityMachineCentrifuge centrifuge;
	
	public GUIMachineCentrifuge(InventoryPlayer invPlayer, TileEntityMachineCentrifuge tedf) {
		super(new ContainerCentrifuge(invPlayer, tedf));
		centrifuge = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.centrifuge.hasCustomInventoryName() ? this.centrifuge.getInventoryName() : I18n.format(this.centrifuge.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(centrifuge.hasPower())
		{
			int i1 = centrifuge.getPowerRemainingScaled(54);
			drawTexturedModalRect(guiLeft + 8, guiTop + 69 - i1, 177, 107 - i1, 16, i1);
		}

		if(centrifuge.isProcessing())
		{
			int j1 = centrifuge.getCentrifugeProgressScaled(55);
			drawTexturedModalRect(guiLeft + 61, guiTop + 16, 176, 0, j1, 54);
		}
		
		if(centrifuge.hasPower() && centrifuge.canProcess()) {
			drawTexturedModalRect(guiLeft + 25, guiTop + 34, 194, 54, 18, 18);
		}
	}
}
