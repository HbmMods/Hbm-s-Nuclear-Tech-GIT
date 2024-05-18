package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineArcFurnaceLarge;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnaceLarge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineArcFurnaceLarge extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_arc_furnace.png");
	private TileEntityMachineArcFurnaceLarge arc;

	public GUIMachineArcFurnaceLarge(InventoryPlayer invPlayer, TileEntityMachineArcFurnaceLarge arc) {
		super(new ContainerMachineArcFurnaceLarge(invPlayer, arc));
		this.arc = arc;
		
		this.xSize = 176;
		this.ySize = 240;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		this.drawElectricityInfo(this, x, y, guiLeft + 8, guiTop + 36, 7, 70, arc.getPower(), arc.getMaxPower());
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.arc.hasCustomInventoryName() ? this.arc.getInventoryName() : I18n.format(this.arc.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int p = (int) (arc.power * 70 / arc.maxPower);
		drawTexturedModalRect(guiLeft + 8, guiTop + 106 - p, 176, 70 - p, 7, p);
	}
}
