package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineTeleporter;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineTeleporter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineTeleporter extends GuiContainer {

	private static ResourceLocation texture = new ResourceLocation(
			RefStrings.MODID + ":textures/gui/gui_teleporter.png");
	private TileEntityMachineTeleporter diFurnace;

	public GUIMachineTeleporter(InventoryPlayer invPlayer, TileEntityMachineTeleporter tedf) {
		super(new ContainerMachineTeleporter(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 86;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format("container.teleporter");

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6,
				4210752);
		this.fontRendererObj.drawString("Power: " + diFurnace.power + "HE/" + TileEntityMachineTeleporter.maxPower + "HE", 10, 20,
				13882323);
		this.fontRendererObj.drawString("Mode: " + (diFurnace.mode ? "Send" : "Receive"), 10, 29, 13882323);
		if (diFurnace.mode) {
			this.fontRendererObj.drawString("Destination X: " + diFurnace.targetX, 10, 38, 13882323);
			this.fontRendererObj.drawString("Destination Y: " + diFurnace.targetY, 10, 47, 13882323);
			this.fontRendererObj.drawString("Destination Z: " + diFurnace.targetZ, 10, 56, 13882323);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
