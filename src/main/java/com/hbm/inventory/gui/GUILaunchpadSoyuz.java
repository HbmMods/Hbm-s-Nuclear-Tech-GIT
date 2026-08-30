package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerLaunchpadSoyuz;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityLaunchpadSoyuz;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUILaunchpadSoyuz extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_launchpad_soyuz.png");
	private TileEntityLaunchpadSoyuz launcher;
	
	public GUILaunchpadSoyuz(InventoryPlayer invPlayer, TileEntityLaunchpadSoyuz tedf) {
		super(new ContainerLaunchpadSoyuz(invPlayer, tedf));
		launcher = tedf;
		
		this.xSize = 194;
		this.ySize = 244;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		launcher.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 44, 16, 52);
		launcher.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 170, guiTop + 44, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 44, 16, 52, launcher.power, launcher.maxPower);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.launcher.hasCustomInventoryName() ? this.launcher.getInventoryName() : I18n.format(this.launcher.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 4, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 17, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		launcher.tanks[0].renderTank(guiLeft + 152, guiTop + 96, this.zLevel, 16, 52);
		launcher.tanks[1].renderTank(guiLeft + 170, guiTop + 96, this.zLevel, 16, 52);
	}
}
