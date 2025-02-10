package com.hbm.inventory.gui;

import java.util.Arrays;
import java.util.Locale;

import com.hbm.render.util.GaugeUtil;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.SlotPattern;
import com.hbm.inventory.container.ContainerMachineCustom;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.machine.TileEntityCustomMachine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCustom extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_custom.png");
	private TileEntityCustomMachine custom;

	public GUIMachineCustom(InventoryPlayer invPlayer, TileEntityCustomMachine tedf) {
		super(new ContainerMachineCustom(invPlayer, tedf));
		custom = tedf;

		this.xSize = 176;
		this.ySize = 256;
	}

	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		this.drawElectricityInfo(this, x, y, guiLeft + 150, guiTop + 18, 16, 52, custom.power, custom.config.maxPower);
		if(custom.config.maxHeat>0) this.drawCustomInfoStat(x, y, guiLeft + 61, guiTop + 53, 18, 18, x, y, new String[] { "Heat:" + String.format(Locale.US, "%,d", custom.heat) + " / " + String.format(Locale.US, "%,d", custom.config.maxHeat)});
		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for(int i = 0; i < this.inventorySlots.inventorySlots.size(); ++i) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
				int tileIndex = slot.getSlotIndex();

				if(this.isMouseOverSlot(slot, x, y) && slot instanceof SlotPattern && custom.matcher.modes[tileIndex - 10] != null) {
					this.func_146283_a(Arrays.asList(new String[] { EnumChatFormatting.RED + "Right click to change", ModulePatternMatcher.getLabel(custom.matcher.modes[tileIndex - 10]) }), x, y - 30);
				}
			}
		}

		for(int i = 0; i < custom.inputTanks.length; i++) {
			custom.inputTanks[i].renderTankInfo(this, x, y, guiLeft + 8 + 18 * i, guiTop + 18, 16, 34);
		}

		for(int i = 0; i < custom.outputTanks.length; i++) {
			custom.outputTanks[i].renderTankInfo(this, x, y, guiLeft + 78 + 18 * i, guiTop + 18, 16, 34);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.custom.getInventoryName();
		String localizedName = this.custom.config.localization.get(MainRegistry.proxy.getLanguageCode());
		if(localizedName != null) name = localizedName;
		this.fontRendererObj.drawString(name, 68 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		if(custom.config.fluxMode) this.fontRendererObj.drawString("Flux:" + custom.flux,83, 57,0x08FF00);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if(custom.config.fluxMode){
			drawTexturedModalRect(guiLeft + 78, guiTop + 54, 192, 122,51 , 15);
		}
		if(custom.maxHeat > 0) {
			drawTexturedModalRect(guiLeft + 61, guiTop + 53, 236, 0, 18, 18);
			GaugeUtil.drawSmoothGauge(guiLeft + 70, guiTop + 62, this.zLevel, (double) custom.heat / (double) custom.config.maxHeat, 5, 2, 1, 0x7F0000);
		}
		int p = custom.progress * 90 / custom.maxProgress;
		drawTexturedModalRect(guiLeft + 78, guiTop + 119, 192, 0, Math.min(p, 44), 16);
		if(p > 44) {
			p -= 44;
			drawTexturedModalRect(guiLeft + 78 + 44, guiTop + 119, 192, 16, p, 16);
		}

		int e = (int) (custom.power * 52 / custom.config.maxPower);
		drawTexturedModalRect(guiLeft + 150, guiTop + 70 - e, 176, 52 - e, 16, e);

		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				int index = i * 3 + j;
				if(custom.config.itemInCount <= index) {
					drawTexturedModalRect(guiLeft + 7 + j * 18, guiTop + 71 + i * 18, 192 + j * 18, 86 + i * 18, 18, 18);
					drawTexturedModalRect(guiLeft + 7 + j * 18, guiTop + 107 + i * 18, 192 + j * 18, 86 + i * 18, 18, 18);
				}
				if(custom.config.itemOutCount <= index) {
					drawTexturedModalRect(guiLeft + 77 + j * 18, guiTop + 71 + i * 18, 192 + j * 18, 86 + i * 18, 18, 18);
				}
			}
		}

		for(int i = 0; i < 3; i++) {
			if(custom.config.fluidInCount <= i) {
				drawTexturedModalRect(guiLeft + 7 + i * 18, guiTop + 17, 192 + i * 18, 32, 18, 54);
			}
			if(custom.config.fluidOutCount <= i) {
				drawTexturedModalRect(guiLeft + 77 + i * 18, guiTop + 17, 192 + i * 18, 32, 18, 36);
			}
		}

		for(int i = 0; i < custom.inputTanks.length; i++) {
			custom.inputTanks[i].renderTank(guiLeft + 8 + 18 * i, guiTop + 52, this.zLevel, 16, 34);
		}

		for(int i = 0; i < custom.outputTanks.length; i++) {
			custom.outputTanks[i].renderTank(guiLeft + 78 + 18 * i, guiTop + 52, this.zLevel, 16, 34);
		}
	}

}
