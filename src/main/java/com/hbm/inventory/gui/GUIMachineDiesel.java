package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerMachineDiesel;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineDiesel;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineDiesel extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/GUIDiesel.png");
	private TileEntityMachineDiesel diFurnace;

	public GUIMachineDiesel(InventoryPlayer invPlayer, TileEntityMachineDiesel tedf) {
		super(new ContainerMachineDiesel(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 69 - 52, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 69 - 52, 16, 52, diFurnace.power, diFurnace.powerCap);

		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.YELLOW + "Accepted Fuels:");
		
		for(Entry<FluidType, Integer> entry : TileEntityMachineDiesel.fuels.entrySet()) {
			text.add("  " + I18nUtil.resolveKey(entry.getKey().getUnlocalizedName()) + " (" + entry.getValue() + " HE/t)");
		}
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text.toArray(new String[0]));
		
		String[] text1 = new String[] { "Fuel consumption rate:",
				"  1 mB/t",
				"  20 mB/s",
				"(Consumption rate is constant)" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
		
		if(!diFurnace.hasAcceptableFuel()) {
			
			String[] text2 = new String[] { "Error: The currently set fuel type",
					"is not supported by this engine!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32, 16, 16, guiLeft - 8, guiTop + 36 + 16 + 32, text2);
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
			int i = (int)diFurnace.getPowerScaled(52);
			drawTexturedModalRect(guiLeft + 152, guiTop + 69 - i, 176, 52 - i, 16, i);
		}
		
		if(diFurnace.tank.getFill() > 0 && diFurnace.hasAcceptableFuel())
		{
			drawTexturedModalRect(guiLeft + 43 + 18 * 4, guiTop + 34, 208, 0, 18, 18);
		}

		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);
		
		if(!diFurnace.hasAcceptableFuel())
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(diFurnace.tank.getSheet());
		diFurnace.tank.renderTank(this, guiLeft + 80, guiTop + 69, diFurnace.tank.getTankType().textureX() * FluidTank.x, diFurnace.tank.getTankType().textureY() * FluidTank.y, 16, 52);

		/*Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.missileNuclear_tex);
		GL11.glPushMatrix();
		GL11.glTranslatef(guiLeft + 88, guiTop + 110, 50);
		GL11.glRotatef(System.currentTimeMillis() / 10 % 360, 0, -1, 0);
		GL11.glTranslatef(60, 0, 0);
		GL11.glScalef(16, 16, 16);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glScalef(-1, -1, -1);
		ResourceManager.missileNuclear.renderAll();
		GL11.glPopMatrix();*/
	}
}
