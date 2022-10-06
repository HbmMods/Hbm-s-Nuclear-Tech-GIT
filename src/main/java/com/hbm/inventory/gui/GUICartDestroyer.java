package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCartDestroyer;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUICartDestroyer extends GuiContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/cart/gui_destroyer.png");
	private IInventory cart;
	
	public GUICartDestroyer(InventoryPlayer invPlayer, IInventory inv) {
		super(new ContainerCartDestroyer(invPlayer, inv));
		cart = inv;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.cart.hasCustomInventoryName() ? this.cart.getInventoryName() : I18n.format(this.cart.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 4, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int time = (int) (System.currentTimeMillis() % 1000);
		int index = time / 128;

		if(index == 1 || index == 7) drawTexturedModalRect(guiLeft + 66, guiTop + 35, 0, 166, 44, 16);
		if(index == 2 || index == 6) drawTexturedModalRect(guiLeft + 66, guiTop + 35, 0, 182, 44, 16);
		if(index == 3 || index == 5) drawTexturedModalRect(guiLeft + 66, guiTop + 35, 0, 198, 44, 16);
		if(index == 4) drawTexturedModalRect(guiLeft + 66, guiTop + 35, 0, 214, 44, 16);
	}
}
