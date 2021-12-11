package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCanduCore;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.candu.TileEntityCanduCore;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICanduCore extends GuiInfoContainer{
	
	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_candu_core.png");
	private TileEntityCanduCore core;

	public GUICanduCore(InventoryPlayer invPlayer, TileEntityCanduCore core) {
		super(new ContainerCanduCore(invPlayer, core));
		this.xSize = 176;
		this.ySize = 256;
		
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}

	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
