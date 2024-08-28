package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineMilkReformer;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineMilkReformer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMilkReformer extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_milk_reformer.png");
	private TileEntityMachineMilkReformer refinery;

	public GUIMilkReformer(InventoryPlayer invPlayer, TileEntityMachineMilkReformer tedf) {
		super(new ContainerMachineMilkReformer(invPlayer, tedf));
		refinery = tedf;
		
		this.xSize = 176;
		this.ySize = 238;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		refinery.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 45, guiTop + 90 - 52, 16, 29);
		refinery.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 90, guiTop + 70 - 15, 21, 10);
		refinery.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 117, guiTop + 70 - 15, 21, 10);
		refinery.tanks[3].renderTankInfo(this, mouseX, mouseY, guiLeft + 143, guiTop + 70 - 15, 21, 10);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 98,  guiTop + 60 - 52, 70, 20, refinery.power, TileEntityMachineMilkReformer.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {		
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int j = (int) (refinery.power * 63 / TileEntityMachineMilkReformer.maxPower);
		drawTexturedModalRect(guiLeft + 98, guiTop + 13, 176, 6, j, 6);
		
		refinery.tanks[0].renderTank(guiLeft + 50, guiTop + 62, this.zLevel, 5, 30);
		refinery.tanks[1].renderTank(guiLeft + 101, guiTop + 63, this.zLevel, 3, 10);
		refinery.tanks[2].renderTank(guiLeft + 128, guiTop + 63, this.zLevel, 3, 10);
		refinery.tanks[3].renderTank(guiLeft + 155, guiTop + 63, this.zLevel, 3, 10);
	}
}
