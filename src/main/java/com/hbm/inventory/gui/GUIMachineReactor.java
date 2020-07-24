package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.BreederRecipes;
import com.hbm.inventory.container.ContainerReactor;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineReactor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineReactor extends GuiInfoContainer {
	
	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_breeder.png");
	private TileEntityMachineReactor breeder;

	public GUIMachineReactor(InventoryPlayer invPlayer, TileEntityMachineReactor tedf) {
		super(new ContainerReactor(invPlayer, tedf));
		breeder = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String tooltip = BreederRecipes.getHEATString(breeder.heat + " HEAT", breeder.heat);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 47, guiTop + 34, 6, 18, mouseX, mouseY, new String[] { tooltip });
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 55, guiTop + 34, 18, 18, mouseX, mouseY, new String[] { breeder.charge + " operation(s) left" });
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.breeder.hasCustomInventoryName() ? this.breeder.getInventoryName() : I18n.format(this.breeder.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(breeder.hasPower())
			drawTexturedModalRect(guiLeft + 55, guiTop + 35, 176, 0, 18, 16);
		
		int i = breeder.getProgressScaled(23);
		drawTexturedModalRect(guiLeft + 80, guiTop + 34, 176, 16, i, 16);
		
		int j = breeder.getHeatScaled(16);
		drawTexturedModalRect(guiLeft + 48, guiTop + 51 - j, 194, 16 - j, 4, j);
	}

}