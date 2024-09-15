package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPyroOven;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityMachinePyroOven;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIPyroOven extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_pyrooven.png");
	private TileEntityMachinePyroOven pyro;

	public GUIPyroOven(InventoryPlayer playerInv, TileEntityMachinePyroOven tile) {
		super(new ContainerPyroOven(playerInv, tile));
		
		this.pyro = tile;
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		pyro.tanks[0].renderTankInfo(this, x, y, guiLeft + 8, guiTop + 18, 16, 52);
		pyro.tanks[1].renderTankInfo(this, x, y, guiLeft + 116, guiTop + 18, 16, 52);
		this.drawElectricityInfo(this, x, y, guiLeft + 152, guiTop + 18, 16, 52, pyro.getPower(), pyro.getMaxPower());
		
		this.drawCustomInfoStat(x, y, guiLeft + 108, guiTop + 76, 8, 8, guiLeft + 108, guiTop + 76, this.getUpgradeInfo(pyro));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.pyro.hasCustomInventoryName() ? this.pyro.getInventoryName() : I18n.format(this.pyro.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2 - 18, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int i = (int) (pyro.power * 52 / pyro.maxPower);
		drawTexturedModalRect(guiLeft + 152, guiTop + 70 - i, 176, 64 - i, 16, i);
		
		int p = (int) (pyro.progress * 27);
		drawTexturedModalRect(guiLeft + 57, guiTop + 47, 176, 0, p, 12);

		pyro.tanks[0].renderTank(guiLeft + 8, guiTop + 70, this.zLevel, 16, 52);
		pyro.tanks[1].renderTank(guiLeft + 116, guiTop + 70, this.zLevel, 16, 52);
		
		this.drawInfoPanel(guiLeft + 108, guiTop + 76, 8, 8, 8);
	}
}
