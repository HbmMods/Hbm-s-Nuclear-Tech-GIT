package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineCyclotron;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCyclotron extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_cyclotron.png");
	private TileEntityMachineCyclotron cyclotron;

	public GUIMachineCyclotron(InventoryPlayer invPlayer, TileEntityMachineCyclotron tile) {
		super(new ContainerMachineCyclotron(invPlayer, tile));
		cyclotron = tile;

		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 72, 7, 52, cyclotron.power, cyclotron.maxPower);

		cyclotron.coolant.renderTankInfo(this, mouseX, mouseY, guiLeft + 53, guiTop + 72, 7, 52);
		cyclotron.amat.renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 90, 7, 34);

		String[] upgradeText = new String[4];
		upgradeText[0] = I18nUtil.resolveKey("desc.gui.upgrade");
		upgradeText[1] = I18nUtil.resolveKey("desc.gui.upgrade.speed");
		upgradeText[2] = I18nUtil.resolveKey("desc.gui.upgrade.effectiveness");
		upgradeText[3] = I18nUtil.resolveKey("desc.gui.upgrade.power");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 21, guiTop + 75, 8, 8, mouseX, mouseY, upgradeText);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.cyclotron.hasCustomInventoryName() ? this.cyclotron.getInventoryName() : I18n.format(this.cyclotron.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 97 <= x && guiLeft + 97 + 18 > x && guiTop + 107 < y && guiTop + 107 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(cyclotron.xCoord, cyclotron.yCoord, cyclotron.zCoord, 0, 0));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int k = (int) cyclotron.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 80, guiTop + 124 - k, 212, 52 - k, 7, k);

		int l = cyclotron.getProgressScaled(36);
		drawTexturedModalRect(guiLeft + 52, guiTop + 26, 176, 0, l, 36);

		if(cyclotron.isOn)
			drawTexturedModalRect(guiLeft + 97, guiTop + 107, 219, 0, 18, 18);

		this.drawInfoPanel(guiLeft + 21, guiTop + 75, 8, 8, 8);

		cyclotron.coolant.renderTank(guiLeft + 53, guiTop + 124, this.zLevel, 7, 52);
		cyclotron.amat.renderTank(guiLeft + 134, guiTop + 124, this.zLevel, 7, 34);
	}
}
