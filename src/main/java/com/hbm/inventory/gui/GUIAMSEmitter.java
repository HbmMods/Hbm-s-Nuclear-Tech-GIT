package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerAMSEmitter;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIAMSEmitter extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_ams_emitter.png");
	private TileEntityAMSEmitter emitter;
	
	public GUIAMSEmitter(InventoryPlayer invPlayer, TileEntityAMSEmitter tedf) {
		super(new ContainerAMSEmitter(invPlayer, tedf));
		emitter = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		emitter.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 69 - 52, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 69 - 52, 16, 52, emitter.power, emitter.maxPower);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 69 - 52, 16, 52, new String[] { "Power:", emitter.efficiency + "%" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 69 - 52, 16, 52, new String[] { "Heat:", emitter.heat + "/" + emitter.maxHeat });
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.emitter.hasCustomInventoryName() ? this.emitter.getInventoryName() : I18n.format(this.emitter.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int) emitter.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 134, guiTop + 69 - i, 192, 52 - i, 16, i);
		
		int j = emitter.getEfficiencyScaled(52);
		drawTexturedModalRect(guiLeft + 152, guiTop + 69 - j, 208, 52 - j, 16, j);
		
		int k = emitter.getHeatScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 69 - k, 176, 52 - k, 16, k);
		
		int m = emitter.warning;
		if(m > 0)
			drawTexturedModalRect(guiLeft + 80, guiTop + 17, 176, 36 + 16 * m, 16, 16);
		
		emitter.tank.renderTank(guiLeft + 26, guiTop + 69, this.zLevel, 16, 52);
	}
}
