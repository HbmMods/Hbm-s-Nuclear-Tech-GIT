package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineCyclotron;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCyclotron extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_cyclotron.png");
	private TileEntityMachineCyclotron cyclotron;

	public GUIMachineCyclotron(InventoryPlayer invPlayer, TileEntityMachineCyclotron tile) {
		super(new ContainerMachineCyclotron(invPlayer, tile));
		cyclotron = tile;

		this.xSize = 190;
		this.ySize = 215;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 168, guiTop + 18, 16, 63, cyclotron.power, cyclotron.maxPower);

		cyclotron.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 11, guiTop + 81, 34, 7);
		cyclotron.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 11, guiTop + 90, 34, 7);
		cyclotron.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 107, guiTop + 81, 34, 16);

		String[] upgradeText = new String[4];
		upgradeText[0] = I18nUtil.resolveKey("desc.gui.upgrade");
		upgradeText[1] = I18nUtil.resolveKey("desc.gui.upgrade.speed");
		upgradeText[2] = I18nUtil.resolveKey("desc.gui.upgrade.effectiveness");
		upgradeText[3] = I18nUtil.resolveKey("desc.gui.upgrade.power");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 49, guiTop + 85, 8, 8, mouseX, mouseY, upgradeText);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.cyclotron.hasCustomInventoryName() ? this.cyclotron.getInventoryName() : I18n.format(this.cyclotron.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 15, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int k = (int) cyclotron.getPowerScaled(63);
		drawTexturedModalRect(guiLeft + 168, guiTop + 80 - k, 190, 62 - k, 16, k);
		
		int l = cyclotron.getProgressScaled(34);
		drawTexturedModalRect(guiLeft + 48, guiTop + 27, 206, 0, l, 34);
		
		if(l > 0)
			drawTexturedModalRect(guiLeft + 172, guiTop + 4, 190, 63, 9, 12);
		
		this.drawInfoPanel(guiLeft + 49, guiTop + 85, 8, 8, 8);
		
		cyclotron.tanks[0].renderTank(guiLeft + 11, guiTop + 88, this.zLevel, 34, 7, 1);
		cyclotron.tanks[1].renderTank(guiLeft + 11, guiTop + 97, this.zLevel, 34, 7, 1);
		cyclotron.tanks[2].renderTank(guiLeft + 107, guiTop + 97, this.zLevel, 34, 16, 1);
	}
}
