package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineShredderLarge;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityMachineShredderLarge;

import api.hbm.energy.IBatteryItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import net.minecraft.util.ResourceLocation;

public class GUIMachineShredderLarge extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_big_shredder.png");
	private TileEntityMachineShredderLarge triFurnace;

	public GUIMachineShredderLarge(InventoryPlayer invPlayer, TileEntityMachineShredderLarge tedf) {
		super(new ContainerMachineShredderLarge(invPlayer, tedf));
		triFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		triFurnace.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 43, guiTop + 23, 7, 52);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 106 - 88, 16, 88, triFurnace.power, triFurnace.maxPower);
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.triFurnace.hasCustomInventoryName() ? this.triFurnace.getInventoryName() : I18n.format(this.triFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(triFurnace.power > 0) {
			int i = (int)
					triFurnace.getPowerScaled(88);
			drawTexturedModalRect(guiLeft + 8, guiTop + 106 - i, 176, 160 - i, 16, i);
		}
		
		triFurnace.tank.renderTank(guiLeft + 43, guiTop + 75, this.zLevel, 7, 52);
		
	}
	
}
