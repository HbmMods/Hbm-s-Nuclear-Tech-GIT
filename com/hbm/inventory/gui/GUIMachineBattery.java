package com.hbm.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineBattery;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineBattery;

public class GUIMachineBattery extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_battery.png");
	private TileEntityMachineBattery diFurnace;

	public GUIMachineBattery(InventoryPlayer invPlayer, TileEntityMachineBattery tedf) {
		super(new ContainerMachineBattery(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 52, 52, diFurnace.power, diFurnace.maxPower);

		String[] text = new String[] { "Only stores power by default.",
				"Apply redstone signal to set it to",
				"output mode." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		name += (" (" + this.diFurnace.power + " HE)");
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(diFurnace.power > 0) {
			int i = (int)diFurnace.getPowerRemainingScaled(52);
			drawTexturedModalRect(guiLeft + 62, guiTop + 69 - i, 176, 52 - i, 52, i);
		}

		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
	}
}
