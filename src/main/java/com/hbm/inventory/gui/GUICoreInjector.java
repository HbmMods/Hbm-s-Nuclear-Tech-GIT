package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCoreInjector;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityCoreInjector;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICoreInjector extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/dfc/gui_injector.png");
	private TileEntityCoreInjector injector;
	
	public GUICoreInjector(InventoryPlayer invPlayer, TileEntityCoreInjector tedf) {
		super(new ContainerCoreInjector(invPlayer, tedf));
		injector = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		injector.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 44, guiTop + 17, 16, 52);
		injector.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 17, 16, 52);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		
		String name = this.injector.hasCustomInventoryName() ? this.injector.getInventoryName() : I18n.format(this.injector.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		injector.tanks[0].renderTank(guiLeft + 44, guiTop + 69, this.zLevel, 16, 52);
		injector.tanks[1].renderTank(guiLeft + 116, guiTop + 69, this.zLevel, 16, 52);
	}
}
