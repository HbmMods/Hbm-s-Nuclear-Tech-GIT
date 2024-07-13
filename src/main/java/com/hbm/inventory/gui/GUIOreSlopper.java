package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerOreSlopper;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineOreSlopper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIOreSlopper extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_ore_slopper.png");
	private TileEntityMachineOreSlopper slopper;

	public GUIOreSlopper(InventoryPlayer player, TileEntityMachineOreSlopper slopper) {
		super(new ContainerOreSlopper(player, slopper));
		this.slopper = slopper;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		slopper.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 18, 34, 52);
		slopper.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 18, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 18, 16, 52, slopper.power, slopper.maxPower);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		
		String name = this.slopper.hasCustomInventoryName() ? this.slopper.getInventoryName() : I18n.format(this.slopper.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2 - 9, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int) (slopper.progress * 35);
		drawTexturedModalRect(guiLeft + 62, guiTop + 52 - i, 176, 34 - i, 34, i);
		
		int j = (int) (slopper.power * 52 / slopper.maxPower);
		drawTexturedModalRect(guiLeft + 8, guiTop + 70 - j, 176, 86 - j, 16, j);
		
		if(slopper.power >= slopper.consumption)
			drawTexturedModalRect(guiLeft + 12, guiTop + 4, 202, 34, 9, 12);

		slopper.tanks[0].renderTank(guiLeft + 26, guiTop + 70, this.zLevel, 16, 52);
		slopper.tanks[1].renderTank(guiLeft + 116, guiTop + 70, this.zLevel, 16, 52);
	}
}
