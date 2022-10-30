package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerStorageDrum;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityStorageDrum;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIStorageDrum extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_drum.png");
	private TileEntityStorageDrum drum;

	public GUIStorageDrum(InventoryPlayer invPlayer, TileEntityStorageDrum tedf) {
		super(new ContainerStorageDrum(invPlayer, tedf));
		drum = tedf;
		
		this.xSize = 176;
		this.ySize = 234;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		drum.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 16, guiTop + 23, 9, 108);
		drum.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 151, guiTop + 23, 9, 108);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.drum.hasCustomInventoryName() ? this.drum.getInventoryName() : I18n.format(this.drum.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int liquid = drum.tanks[0].getFill() * 106 / drum.tanks[0].getMaxFill();
		drawTexturedModalRect(guiLeft + 17, guiTop + 130 - liquid, 176, 106 - liquid, 7, liquid);
		int gas = drum.tanks[1].getFill() * 106 / drum.tanks[1].getMaxFill();
		drawTexturedModalRect(guiLeft + 152, guiTop + 130 - gas, 183, 106 - gas, 7, gas);
	}
}
