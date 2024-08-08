package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerVacuumCircuit;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineVacuumCircuit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIVacuumCircuit extends GuiInfoContainer {


	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_vac_cir_station.png");
	private TileEntityMachineVacuumCircuit sucker;

	public GUIVacuumCircuit(InventoryPlayer playerInv, TileEntityMachineVacuumCircuit tile) {
		super(new ContainerVacuumCircuit(playerInv, tile));
		
		this.sucker = tile;
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		this.drawElectricityInfo(this, x, y, guiLeft + 132, guiTop + 18, 16, 52, sucker.getPower(), sucker.getMaxPower());
		
		this.drawCustomInfoStat(x, y, guiLeft + 52, guiTop + 19, 8, 8, guiLeft + 52, guiTop + 19, this.getUpgradeInfo(sucker));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.sucker.hasCustomInventoryName() ? this.sucker.getInventoryName() : I18n.format(this.sucker.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2 - 18, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int p = (int) (sucker.power * 52 / Math.max(sucker.maxPower, 1));
		drawTexturedModalRect(guiLeft + 132, guiTop + 70 - p, 176, 52 - p, 16, p);

		int i = sucker.progress * 33 / Math.max(sucker.processTime, 1);
		drawTexturedModalRect(guiLeft + 50, guiTop + 49, 192, 0, i, 14);
		
		if(sucker.power >= sucker.consumption) {
			drawTexturedModalRect(guiLeft + 135, guiTop + 4, 176, 52, 9, 12);
		}

		this.drawInfoPanel(guiLeft + 52, guiTop + 19, 8, 8, 8);
	}
}
