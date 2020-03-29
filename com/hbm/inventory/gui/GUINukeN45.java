package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeN45;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityNukeN45;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINukeN45 extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_n45.png");
	private TileEntityNukeN45 diFurnace;
	
	public GUINukeN45(InventoryPlayer invPlayer, TileEntityNukeN45 tedf) {
		super(new ContainerNukeN45(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		String[] text = new String[] { "The first slot holds the payload.",
				"Acceptable payloads:",
				" -Det Cord",
				" -TNT",
				" -Explosive Charge",
				" -Nuclear Charge",
				"Using detonator while in mine mode will",
				"arm the mine, set to explode when",
				"it detects a large entity nearby."};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		
		String[] text1 = new String[] { "The second slot is for green machine",
				"upgrades. Entity detection range increases",
				"by 5 blocks for every level.",
				"When left empty, the mine can not be armed",
				"an will behave like a regular bomb." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
		
		if(diFurnace.primed)
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 121, guiTop + 22, 6, 8, mouseX, mouseY, new String[]{ "Mine armed!" } );
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(diFurnace.primed) {
			drawTexturedModalRect(guiLeft + 121, guiTop + 22, 176, 0, 6, 8);
		}

		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);
	}
}
