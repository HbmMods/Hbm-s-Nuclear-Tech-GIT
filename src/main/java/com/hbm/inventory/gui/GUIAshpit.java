package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerAshpit;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityAshpit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIAshpit extends GuiInfoContainer {
	
	private TileEntityAshpit firebox;
	private final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_ashpit.png");

	public GUIAshpit(InventoryPlayer invPlayer, TileEntityAshpit tedf) {
		super(new ContainerAshpit(invPlayer, tedf));
		firebox = tedf;
		
		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.firebox.hasCustomInventoryName() ? this.firebox.getInventoryName() : I18n.format(this.firebox.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
