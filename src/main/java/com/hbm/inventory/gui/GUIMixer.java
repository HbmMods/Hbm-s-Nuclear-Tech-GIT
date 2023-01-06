package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMixer;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineMixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMixer extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_mixer.png");
	private TileEntityMachineMixer mixer;

	public GUIMixer(InventoryPlayer player, TileEntityMachineMixer mixer) {
		super(new ContainerMixer(player, mixer));
		this.mixer = mixer;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		this.drawElectricityInfo(this, x, y, guiLeft + 23, guiTop + 23, 16, 52, mixer.getPower(), mixer.getMaxPower());

		mixer.tanks[0].renderTankInfo(this, x, y, guiLeft + 43, guiTop + 23, 7, 52);
		mixer.tanks[1].renderTankInfo(this, x, y, guiLeft + 52, guiTop + 23, 7, 52);
		mixer.tanks[2].renderTankInfo(this, x, y, guiLeft + 117, guiTop + 23, 16, 52);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		
		String name = this.mixer.hasCustomInventoryName() ? this.mixer.getInventoryName() : I18n.format(this.mixer.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int) (mixer.getPower() * 53 / mixer.getMaxPower());
		drawTexturedModalRect(guiLeft + 23, guiTop + 75 - i, 176, 52 - i, 16, i);
		
		if(mixer.processTime > 0 && mixer.progress > 0) {
			int j = mixer.progress * 53 / mixer.processTime;
			drawTexturedModalRect(guiLeft + 62, guiTop + 36, 192, 0, j, 44);
		}

		mixer.tanks[0].renderTank(guiLeft + 43, guiTop + 75, this.zLevel, 7, 52);
		mixer.tanks[1].renderTank(guiLeft + 52, guiTop + 75, this.zLevel, 7, 52);
		mixer.tanks[2].renderTank(guiLeft + 117, guiTop + 75, this.zLevel, 16, 52);
	}
}
