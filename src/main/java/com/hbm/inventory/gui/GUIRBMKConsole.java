package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRBMKConsole;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKConsole extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_console.png");
	private TileEntityRBMKConsole console;

	public GUIRBMKConsole(InventoryPlayer invPlayer, TileEntityRBMKConsole tedf) {
		super(new ContainerRBMKConsole(invPlayer, tedf));
		this.console = tedf;
		
		this.xSize = 244;
		this.ySize = 172;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 71, guiTop + 29, 16, 56, mouseX, mouseY, new String[]{ "%" } );
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
