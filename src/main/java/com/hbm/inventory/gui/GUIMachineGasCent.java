package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerMachineGasCent;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineGasCent extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/centrifuge_gas.png");
	private TileEntityMachineGasCent gasCent;
	
	public GUIMachineGasCent(InventoryPlayer invPlayer, TileEntityMachineGasCent tedf) {
		super(new ContainerMachineGasCent(invPlayer, tedf));
		gasCent = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 58, guiTop + 30, 8, 33, mouseX, mouseY, new String[] {gasCent.inputTank.getTankType().getName(), gasCent.inputTank.getFill() + " / " + gasCent.inputTank.getMaxFill() + " mB"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 114, guiTop + 30, 8, 33, mouseX, mouseY, new String[] {gasCent.outputTank.getTankType().getName(), gasCent.outputTank.getFill() + " / " + gasCent.outputTank.getMaxFill() + " mB"});
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 51 - 34, 16, 34, gasCent.power, gasCent.maxPower);
		
		String[] info = new String[] { "Uranium enrichment requires cascades.", "Two-centrifuge cascades will give you", "uranium fuel, four-centrifuge cascades", "will give you total separation."};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 16, 16, 16, guiLeft - 8, guiTop + 16 + 16, info);
		String[] info2 = new String[] { "A centrifuge overclocking upgrade is", "required for total isotopic separation."};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 32, 16, 16, guiLeft - 8, guiTop + 32 + 16, info2);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.gasCent.hasCustomInventoryName() ? this.gasCent.getInventoryName() : I18n.format(this.gasCent.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int i = (int)gasCent.getPowerRemainingScaled(34);
		drawTexturedModalRect(guiLeft + 8, guiTop + 51 - i, 176, 34 - i, 16, i);

		int j = (int)gasCent.getCentrifugeProgressScaled(33);
		drawTexturedModalRect(guiLeft + 74, guiTop + 37, 208, 0, j, 12);
		
		int a = gasCent.getTankScaled(32, 0);
		switch (gasCent.inputTank.getTankType()) {
			case PF6:
				drawTexturedModalRect(guiLeft + 58, guiTop + 62 - a, 200, 31 - a, 8, a);
				break;
			case NONE:
				break;
			default:
				drawTexturedModalRect(guiLeft + 58, guiTop + 62 - a, 192, 31 - a, 8, a);
		}
		
		int b = gasCent.getTankScaled(32, 1);
		switch (gasCent.outputTank.getTankType()) {
			case PF6:
				drawTexturedModalRect(guiLeft + 114, guiTop + 62 - b, 200, 31 - b, 8, b);
				break;
			case NONE:
				break;
			default:
				drawTexturedModalRect(guiLeft + 114, guiTop + 62 - b, 192, 31 - b, 8, b);
		}
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 16, 16, 16, 3);
		this.drawInfoPanel(guiLeft - 16, guiTop + 32, 16, 16, 2);
	}
}
