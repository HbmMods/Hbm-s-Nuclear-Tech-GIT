package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineDiFurnaceRTG;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineDiFurnaceRTG;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineDiFurnaceRTG extends GuiInfoContainer
{
	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/processing/gui_rtg_difurnace.png");
	private TileEntityMachineDiFurnaceRTG bFurnace;
	
	public GUIMachineDiFurnaceRTG(InventoryPlayer playerInv, TileEntityMachineDiFurnaceRTG te)
	{
		super(new ContainerMachineDiFurnaceRTG(playerInv, te));
		bFurnace = te;
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
		String[] infoText = new String[] {"Accepted Pellets:",
				"  Pu238 Pellet (+5 heat)",
				"  Weak U238 Pellet (+1 heat)",
				"  Po210 Pellet (+25 heat)",
				"  Au198 Pellet (+150 heat)"
		};
		String[] descText = new String[] {"Requires at least 15 heat to process", "The more heat on top of that, the faster it runs", "Gold-198 can decay into Mercury"};
		String[] heatText = new String[] {String.format("%sCurrent heat level: %s", EnumChatFormatting.YELLOW, bFurnace.getPower())}; 
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 15, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, infoText);
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 15, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, descText);
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 58, guiTop + 36, 18, 16, mouseX, mouseY, heatText);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		String name = this.bFurnace.hasCustomInventoryName() ? this.bFurnace.getInventoryName() : I18n.format(this.bFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if (bFurnace.hasPower())
			drawTexturedModalRect(guiLeft + 58, guiTop + 36, 176, 31, 18, 16);
		
		int p = bFurnace.getDiFurnaceProgressScaled(24);
		drawTexturedModalRect(guiLeft + 101, guiTop + 35, 176, 14, p + 1, 17);
		this.drawInfoPanel(guiLeft - 15, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 15, guiTop + 36 + 16, 16, 16, 3);
	}

}
