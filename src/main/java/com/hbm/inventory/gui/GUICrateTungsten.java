package com.hbm.inventory.gui;

import com.hbm.tileentity.machine.storage.TileEntityCrateTungsten;
import net.minecraft.inventory.IInventory;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCrateTungsten;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICrateTungsten extends GuiContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_crate_tungsten.png");
	private static ResourceLocation texture_hot = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_crate_tungsten_hot.png");
	private IInventory diFurnace;

	public GUICrateTungsten(InventoryPlayer invPlayer, IInventory tedf) {
		super(new ContainerCrateTungsten(invPlayer, tedf));
		setupGUI(tedf);
	}

	private void setupGUI(IInventory tedf) {
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

		if (diFurnace instanceof TileEntityCrateTungsten) {
			TileEntityCrateTungsten crate = (TileEntityCrateTungsten) diFurnace;
			if(crate.getWorldObj().getBlockMetadata(crate.xCoord, crate.yCoord, crate.zCoord) == 0)
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			else
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture_hot);
		} else
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture); // Assuming it's in the inventory, we don't need to worry about it somehow being heated :ayo:

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
