package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineExposureChamber;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineExposureChamber;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineExposureChamber extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_exposure_chamber.png");
	private TileEntityMachineExposureChamber chamber;

	public GUIMachineExposureChamber(InventoryPlayer invPlayer, TileEntityMachineExposureChamber chamber) {
		super(new ContainerMachineExposureChamber(invPlayer, chamber));
		this.chamber = chamber;

		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 18, 16, 34, chamber.power, chamber.maxPower);
		
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 26, guiTop + 36, 9, 16, mouseX, mouseY, chamber.savedParticles + " / " + chamber.maxParticles);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.chamber.hasCustomInventoryName() ? this.chamber.getInventoryName() : I18n.format(this.chamber.getInventoryName());
		this.fontRendererObj.drawString(name, 70 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int p = chamber.progress * 42 / chamber.processTime;
		drawTexturedModalRect(guiLeft + 36, guiTop + 39, 192, 0, p, 10);
		
		int c = chamber.savedParticles * 16 / chamber.maxParticles;
		drawTexturedModalRect(guiLeft + 26, guiTop + 52 - c, 192, 26 - c, 9, c);
		
		int e = (int) (chamber.power * 34 / chamber.maxPower);
		drawTexturedModalRect(guiLeft + 152, guiTop + 52 - e, 176, 34 - e, 16, e);
		
		if(chamber.consumption <= chamber.power) {
			drawTexturedModalRect(guiLeft + 156, guiTop + 4, 176, 34, 9, 12);
		}
	}
}
