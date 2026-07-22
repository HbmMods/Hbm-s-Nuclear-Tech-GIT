package com.hbm.inventory.gui;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPneumoStorageMono;
import com.hbm.inventory.gui.element.GUIElements;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageMono;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoTube;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIPneumoStorageMono extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_pneumatic_mono.png");
	protected TileEntityPneumoStorageMono storage;

	public GUIPneumoStorageMono(InventoryPlayer invPlayer, TileEntityPneumoStorageMono storage) {
		super(new ContainerPneumoStorageMono(invPlayer, storage));
		this.storage = storage;
		
		this.xSize = 200;
		this.ySize = 181;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		this.drawCustomInfoStat(x, y, guiLeft + 174, guiTop + 36, 20, 8, x, y, "Compressor: " + storage.compair.getPressure() + " PU", "Max range: " + TileEntityPneumoTube.getRangeFromPressure(storage.compair.getPressure()) + "m");
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		clickSendFlag(storage, x, y, 174, 36, 20, 8, "pressure");
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.storage.hasCustomInventoryName() ? this.storage.getInventoryName() : I18n.format(this.storage.getInventoryName());
		
		this.fontRendererObj.drawString(name, 176 / 2 - this.fontRendererObj.getStringWidth(name) / 2, 5, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		for(int k = 0; k < 3; k++) {
			if(this.storage.slots[k] != null) {
				int amount = this.storage.amounts[k];
				String percent = " (" + (((int) (amount * 1000D / (double) storage.CAPACITY)) / 10D) + "%)";
				this.fontRendererObj.drawString(String.format(Locale.US, "%,d", amount) + percent, 50, 22 + k * 18, 0x000000);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		for(int i = 0; i < 3; i++) {
			if(this.storage.slots[i] != null) {
				int bar = this.storage.amounts[i] * 124 / this.storage.CAPACITY;
				drawTexturedModalRect(guiLeft + 44, guiTop + 17 + i * 18, 0, 181, bar, 16);
			}
		}

		drawTexturedModalRect(guiLeft + 174 + 4 * (storage.compair.getPressure() - 1), guiTop + 36, 200, 0, 4, 8);
		GUIElements.drawSmoothGauge(guiLeft + 184, guiTop + 25, this.zLevel, (double) storage.compair.getFill() / (double) storage.compair.getMaxFill(), 5, 2, 1, 0xCA6C43, 0xAB4223);
	}
}
