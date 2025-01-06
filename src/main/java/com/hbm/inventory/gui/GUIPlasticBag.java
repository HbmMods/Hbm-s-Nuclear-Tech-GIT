package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPlasticBag;
import com.hbm.items.tool.ItemPlasticBag.InventoryPlasticBag;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIPlasticBag extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_plastic_bag.png");
	private ItemStack firstHeld;
	
	public GUIPlasticBag(InventoryPlayer invPlayer, InventoryPlasticBag box) {
		super(new ContainerPlasticBag(invPlayer, box));
		
		this.xSize = 176;
		this.ySize = 216;
	}

	@Override
	public void drawScreen(int x, int y, float interp) {
		if(firstHeld == null) {
			firstHeld = this.mc.thePlayer.getHeldItem();
		}
		
		super.drawScreen(x, y, interp);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
