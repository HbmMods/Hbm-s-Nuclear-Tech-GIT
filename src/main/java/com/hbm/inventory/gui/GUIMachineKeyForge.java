package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineKeyForge;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineKeyForge;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineKeyForge extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_keyforge.png");
	private TileEntityMachineKeyForge siren;

	public GUIMachineKeyForge(InventoryPlayer invPlayer, TileEntityMachineKeyForge tedf) {
		super(new ContainerMachineKeyForge(invPlayer, tedf));
		siren = tedf;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		String[] keyText = I18nUtil.resolveKeyArray("desc.gui.keyforge.key");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 12, guiTop + 28, 16, 16, guiLeft - 8, guiTop + 36 + 16, keyText);
		
		String[] randomText = I18nUtil.resolveKeyArray("desc.gui.keyforge.random");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 12, guiTop + 28 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, randomText);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.siren.hasCustomInventoryName() ? this.siren.getInventoryName() : I18n.format(this.siren.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		this.drawInfoPanel(guiLeft + 12, guiTop + 28, 16, 16, 2);
		this.drawInfoPanel(guiLeft + 12, guiTop + 28 + 16, 16, 16, 3);
	}
}
