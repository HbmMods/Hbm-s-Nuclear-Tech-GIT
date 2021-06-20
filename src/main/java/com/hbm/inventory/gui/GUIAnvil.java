package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerAnvil;
import com.hbm.lib.RefStrings;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIAnvil extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_anvil.png");

	public GUIAnvil(InventoryPlayer player) {
		super(new ContainerAnvil(player));

		this.xSize = 176;
		this.ySize = 222;

		guiLeft = (this.width - this.xSize) / 2;
		guiTop = (this.height - this.ySize) / 2;
	}

	protected void drawGuiContainerForegroundLayer(int mX, int mY) {
		String name = I18n.format("container.anvil");
		this.fontRendererObj.drawString(name, 61 - this.fontRendererObj.getStringWidth(name) / 2, 8, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float inter, int mX, int mY) {

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);

		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
		this.drawTexturedModalRect(guiLeft + 176, guiTop + 17, 176, 17, 3, 108);
	}
}
