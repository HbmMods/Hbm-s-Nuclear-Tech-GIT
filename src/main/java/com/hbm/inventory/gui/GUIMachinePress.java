package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachinePress;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.GaugeUtil;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachinePress extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_press.png");
	private TileEntityMachinePress press;
	
	public GUIMachinePress(InventoryPlayer invPlayer, TileEntityMachinePress tedf) {
		super(new ContainerMachinePress(invPlayer, tedf));
		press = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 25, guiTop + 16, 18, 18, mouseX, mouseY, (press.speed * 100 / press.maxSpeed) + "%");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 25, guiTop + 34, 18, 18, mouseX, mouseY, (press.burnTime / 200) + " operations left");
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.press.hasCustomInventoryName() ? this.press.getInventoryName() : I18n.format(this.press.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(press.burnTime >= 20) {
			this.drawTexturedModalRect(guiLeft + 27, guiTop + 36, 176, 0, 14, 14);
		}
		
		int k = (int) (press.renderPress * 16 / press.maxPress);
		this.drawTexturedModalRect(guiLeft + 79, guiTop + 35, 194, 0, 18, k);
		
		double i = (double) press.speed / (double) press.maxSpeed;
		GaugeUtil.drawSmoothGauge(guiLeft + 34, guiTop + 25, this.zLevel, i, 5, 2, 1, 0x7f0000);
	}
}