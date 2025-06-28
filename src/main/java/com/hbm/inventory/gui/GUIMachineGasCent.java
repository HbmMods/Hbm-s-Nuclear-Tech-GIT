package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineGasCent;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineGasCent extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_centrifuge_gas.png");
	private TileEntityMachineGasCent gasCent;
	
	public GUIMachineGasCent(InventoryPlayer invPlayer, TileEntityMachineGasCent tedf) {
		super(new ContainerMachineGasCent(invPlayer, tedf));
		gasCent = tedf;

		this.xSize = 206;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] inTankInfo = new String[] {gasCent.inputTank.getTankType().getName(), gasCent.inputTank.getFill() + " / " + gasCent.inputTank.getMaxFill() + " mB"};
		if(gasCent.inputTank.getTankType().getIfHighSpeed()) {
			if(gasCent.getProcessingSpeed() > gasCent.processingSpeed - 70)
				inTankInfo[0] = EnumChatFormatting.DARK_RED + inTankInfo[0];
			else
				inTankInfo[0] = EnumChatFormatting.GOLD + inTankInfo[0];
		}
		String[] outTankInfo = new String[] {gasCent.outputTank.getTankType().getName(), gasCent.outputTank.getFill() + " / " + gasCent.outputTank.getMaxFill() + " mB"};
		if(gasCent.outputTank.getTankType().getIfHighSpeed())
			outTankInfo[0] = EnumChatFormatting.GOLD + outTankInfo[0];
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 15, guiTop + 15, 24, 55, mouseX, mouseY, inTankInfo);
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 137, guiTop + 15, 25, 55, mouseX, mouseY, outTankInfo);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 182, guiTop + 69 - 52, 16, 52, gasCent.power, gasCent.maxPower);
		
		String[] enrichmentText = I18nUtil.resolveKeyArray("desc.gui.gasCent.enrichment");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 12, guiTop + 16, 16, 16, guiLeft - 8, guiTop + 16 + 16, enrichmentText);
		
		String[] transferText = I18nUtil.resolveKeyArray("desc.gui.gasCent.output");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 12, guiTop + 32, 16, 16, guiLeft - 8, guiTop + 32 + 16, transferText);
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

		int i = (int)gasCent.getPowerRemainingScaled(52);
		drawTexturedModalRect(guiLeft + 182, guiTop + 69 - i, 206, 52 - i, 16, i);

		int j = (int)gasCent.getCentrifugeProgressScaled(36);
		drawTexturedModalRect(guiLeft + 70, guiTop + 35, 206, 52, j, 13);
		
		this.renderTank(guiLeft + 16, guiTop + 16, this.zLevel, 6, 52, gasCent.inputTank.getFill(), gasCent.inputTank.getMaxFill());
		this.renderTank(guiLeft + 32, guiTop + 16, this.zLevel, 6, 52, gasCent.inputTank.getFill(), gasCent.inputTank.getMaxFill());
		
		this.renderTank(guiLeft + 138, guiTop + 16, this.zLevel, 6, 52, gasCent.outputTank.getFill(), gasCent.outputTank.getMaxFill());
		this.renderTank(guiLeft + 154, guiTop + 16, this.zLevel, 6, 52, gasCent.outputTank.getFill(), gasCent.outputTank.getMaxFill());
		
		this.drawInfoPanel(guiLeft - 12, guiTop + 16, 16, 16, 3);
		this.drawInfoPanel(guiLeft - 12, guiTop + 32, 16, 16, 2);
	}
	
	public void renderTank(int x, int y, double z, int width, int height, int fluid, int maxFluid) {
		
		GL11.glEnable(GL11.GL_BLEND);
		
		y += height;
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(gasCent.tank.getTankType().getTexture());
		
		int i = (fluid * height) / maxFluid;
		
		double minX = x;
		double maxX = x + width;
		double minY = y - height;
		double maxY = y - (height - i);
		
		double minV = 1D;
		double maxV = 1D - i / 16D;
		double minU = 0D;
		double maxU = width / 16D;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(minX, maxY, z, minU, maxV);
		tessellator.addVertexWithUV(maxX, maxY, z, maxU, maxV);
		tessellator.addVertexWithUV(maxX, minY, z, maxU, minV);
		tessellator.addVertexWithUV(minX, minY, z, minU, minV);
		tessellator.draw();

		GL11.glDisable(GL11.GL_BLEND);
	}
}
