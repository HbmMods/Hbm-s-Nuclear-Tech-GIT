package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerMachineDiesel;
import com.hbm.inventory.container.ContainerMachineTurbofan;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineDiesel;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineTurbofan extends GuiFluidContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_turbofan.png");
	private TileEntityMachineTurbofan diFurnace;

	public GUIMachineTurbofan(InventoryPlayer invPlayer, TileEntityMachineTurbofan tedf) {
		super(new ContainerMachineTurbofan(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 53, guiTop + 69 - 52, 34, 52);
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
			int i = diFurnace.getPowerScaled(52);
			drawTexturedModalRect(guiLeft + 152 - 9, guiTop + 69 - i, 176 + 16, 52 - i, 16, i);
		}
		
		if(diFurnace.afterburner > 0) {
			int i = diFurnace.afterburner;
			drawTexturedModalRect(guiLeft + 107, guiTop + 53, 176, (i - 1) * 16, 16, 16);
		}

		Minecraft.getMinecraft().getTextureManager().bindTexture(FluidTank.fluidTextures);

		diFurnace.tank.renderTank(this, guiLeft + 53, guiTop + 69, diFurnace.tank.getTankType().textureX() * FluidTank.x, diFurnace.tank.getTankType().textureY() * FluidTank.y, 16, 52);
		diFurnace.tank.renderTank(this, guiLeft + 53 + 16, guiTop + 69, diFurnace.tank.getTankType().textureX() * FluidTank.x, diFurnace.tank.getTankType().textureY() * FluidTank.y, 16, 52);
		diFurnace.tank.renderTank(this, guiLeft + 53 + 32, guiTop + 69, diFurnace.tank.getTankType().textureX() * FluidTank.x, diFurnace.tank.getTankType().textureY() * FluidTank.y, 2, 52);
	}
}
