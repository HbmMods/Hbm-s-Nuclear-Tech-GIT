package com.hbm.gui.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.gui.container.ContainerMachineShredder;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityMachineDiesel;
import com.hbm.tileentity.TileEntityMachineShredder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineShredder extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_shredder.png");
	private TileEntityMachineShredder diFurnace;

	public GUIMachineShredder(InventoryPlayer invPlayer, TileEntityMachineShredder tedf) {
		super(new ContainerMachineShredder(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
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
			int i = diFurnace.getPowerScaled(88);
			drawTexturedModalRect(guiLeft + 8, guiTop + 106 - i, 176, 160 - i, 16, i);
		}
		
		if(diFurnace.getGearLeft() != 0)
		{
			int i = diFurnace.getGearLeft();
			if(i == 1)
			{
				drawTexturedModalRect(guiLeft + 43, guiTop + 71, 176, 0, 18, 18);
			}
			if(i == 2)
			{
				drawTexturedModalRect(guiLeft + 43, guiTop + 71, 176, 18, 18, 18);
			}
			if(i == 3)
			{
				drawTexturedModalRect(guiLeft + 43, guiTop + 71, 176, 36, 18, 18);
			}
		}
		
		if(diFurnace.getGearRight() != 0)
		{
			int i = diFurnace.getGearRight();
			if(i == 1)
			{
				drawTexturedModalRect(guiLeft + 79, guiTop + 71, 194, 0, 18, 18);
			}
			if(i == 2)
			{
				drawTexturedModalRect(guiLeft + 79, guiTop + 71, 194, 18, 18, 18);
			}
			if(i == 3)
			{
				drawTexturedModalRect(guiLeft + 79, guiTop + 71, 194, 36, 18, 18);
			}
		}
		
		int j1 = diFurnace.getDiFurnaceProgressScaled(34);
		drawTexturedModalRect(guiLeft + 63, guiTop + 89, 176, 54, j1 + 1, 18);
	}
}
