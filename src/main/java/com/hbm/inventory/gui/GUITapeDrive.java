package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerTapeDrive;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineTapeDrive;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUITapeDrive extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_tape_drive.png");
	private TileEntityMachineTapeDrive tapeDrive;

	public GUITapeDrive(InventoryPlayer invPlayer, TileEntityMachineTapeDrive tedf) {
		super(new ContainerTapeDrive(invPlayer, tedf));
		tapeDrive = tedf;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.tapeDrive.hasCustomInventoryName() ? this.tapeDrive.getInventoryName() : I18n.format(this.tapeDrive.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
