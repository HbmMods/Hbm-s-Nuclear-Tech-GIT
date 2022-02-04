package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerMachineSelenium;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineDiesel;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineSelenium extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_selenium.png");
	private TileEntityMachineSeleniumEngine diFurnace;

	public GUIMachineSelenium(InventoryPlayer invPlayer, TileEntityMachineSeleniumEngine tedf) {
		super(new ContainerMachineSelenium(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 18, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 108, 160, 16, diFurnace.power, diFurnace.powerCap);

		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.YELLOW + "Accepted Fuels:");
		
		for(Entry<FluidType, Integer> entry : TileEntityMachineDiesel.fuels.entrySet()) {
			text.add("  " + I18nUtil.resolveKey(entry.getKey().getUnlocalizedName()) + " (" + entry.getValue() + " HE/t)");
		}

		text.add(EnumChatFormatting.ITALIC + "(These numbers are base values,");
		text.add(EnumChatFormatting.ITALIC + "actual output is based");
		text.add(EnumChatFormatting.ITALIC + "on piston count)");
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text.toArray(new String[0]));
		
		String[] text1 = new String[] { "Fuel consumption rate:",
				"  1 mB/t",
				"  20 mB/s",
				"(Consumption rate per piston)" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
		
		if(diFurnace.pistonCount < 3) {
			
			String[] text2 = new String[] { "Error: At least three pistons are",
					"required to operate this radial engine!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32, 16, 16, guiLeft - 8, guiTop + 36 + 16 + 32, text2);
		}
		
		if(!diFurnace.hasAcceptableFuel()) {
			
			String[] text2 = new String[] { "Error: The currently set fuel type",
					"is not supported by this engine!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 48, 16, 16, guiLeft - 8, guiTop + 36 + 16 + 32, text2);
		}
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
		
		if(diFurnace.power > 0) {
			int i = (int)diFurnace.getPowerScaled(160);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 8, guiTop + 108, 0, 222, i, 16);
		}
		
		if(diFurnace.tank.getFill() > 0 && diFurnace.hasAcceptableFuel() && diFurnace.pistonCount > 2)
		{
			drawTexturedModalRect(guiLeft + 115, guiTop + 71, 192, 0, 18, 18);
		}
		
		if(diFurnace.pistonCount > 0)
		{
			int k = diFurnace.pistonCount;
			drawTexturedModalRect(guiLeft + 26, guiTop + 81, 176, 52 + 16 * k - 16, 16, 16);
		}
		
		if(diFurnace.pistonCount < 3)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);
		
		if(!diFurnace.hasAcceptableFuel())
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 48, 16, 16, 7);
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);
		
		diFurnace.tank.renderTank(guiLeft + 80 + 36, guiTop + 70, this.zLevel, 16, 52);
	}
}
