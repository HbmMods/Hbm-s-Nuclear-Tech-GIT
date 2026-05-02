package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPneumoStorageClutter;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageClutter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIPneumoStorageClutter extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_pneumatic_clutter.png");
	protected TileEntityPneumoStorageClutter storage;

	public GUIPneumoStorageClutter(InventoryPlayer invPlayer, TileEntityPneumoStorageClutter storage) {
		super(new ContainerPneumoStorageClutter(invPlayer, storage));
		this.storage = storage;
		
		this.xSize = 200;
		this.ySize = 235;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.storage.hasCustomInventoryName() ? this.storage.getInventoryName() : I18n.format(this.storage.getInventoryName());
		
		this.fontRendererObj.drawString(name, 176 / 2 - this.fontRendererObj.getStringWidth(name) / 2, 5, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
