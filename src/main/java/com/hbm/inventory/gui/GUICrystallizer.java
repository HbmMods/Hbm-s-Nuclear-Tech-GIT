package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCrystallizer;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICrystallizer extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_crystallizer_alt.png");
	private TileEntityMachineCrystallizer acidomatic;
	
	public GUICrystallizer(InventoryPlayer invPlayer, TileEntityMachineCrystallizer acidomatic) {
		super(new ContainerCrystallizer(invPlayer, acidomatic));
		this.acidomatic = acidomatic;

		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 18, 16, 52, acidomatic.power, acidomatic.maxPower);
		acidomatic.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 35, guiTop + 18, 16, 52);

		String[] upgradeText = new String[4];
		upgradeText[0] = I18nUtil.resolveKey("desc.gui.upgrade");
		upgradeText[1] = I18nUtil.resolveKey("desc.gui.upgrade.speed");
		upgradeText[2] = I18nUtil.resolveKey("desc.gui.upgrade.effectiveness");
		upgradeText[3] = I18nUtil.resolveKey("desc.gui.upgrade.overdrive");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 117, guiTop + 22, 8, 8, guiLeft + 200, guiTop + 45, upgradeText);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.acidomatic.hasCustomInventoryName() ? this.acidomatic.getInventoryName() : I18n.format(this.acidomatic.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int i = (int)acidomatic.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 152, guiTop + 70 - i, 176, 64 - i, 16, i);
		
		int j = acidomatic.getProgressScaled(28);
		drawTexturedModalRect(guiLeft + 80, guiTop + 47, 176, 0, j, 12);
		
		this.drawInfoPanel(guiLeft + 117, guiTop + 22, 8, 8, 8);

		acidomatic.tank.renderTank(guiLeft + 35, guiTop + 70, this.zLevel, 16, 52);
	}
}
