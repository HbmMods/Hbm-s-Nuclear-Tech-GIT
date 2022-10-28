package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCrateTungsten;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.storage.TileEntityCrateTungsten;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICrateTungsten extends GuiContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_crate_tungsten.png");
	private static ResourceLocation texture_hot = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_crate_tungsten_hot.png");
	private TileEntityCrateTungsten diFurnace;
	
	public GUICrateTungsten(InventoryPlayer invPlayer, TileEntityCrateTungsten tedf) {
		super(new ContainerCrateTungsten(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 0xffffff);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		if(diFurnace.getWorldObj().getBlockMetadata(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord) == 0)
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		else
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture_hot);
		
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
