package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerSatDock;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineSatDock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUISatDock extends GuiInfoContainer {

	public static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_dock.png");
	private final TileEntityMachineSatDock tileSatelliteDock;
	
	public GUISatDock(InventoryPlayer invPlayer, TileEntityMachineSatDock tesd) {
		super(new ContainerSatDock(invPlayer, tesd));
		tileSatelliteDock = tesd;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] text = new String[] { "Requires linked miner sat chip.",
				"Cargo ship will land periodically to",
				"deliver resources." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.tileSatelliteDock.hasCustomInventoryName() ? this.tileSatelliteDock.getInventoryName() : I18n.format(this.tileSatelliteDock.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0x404040);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
	}
}
