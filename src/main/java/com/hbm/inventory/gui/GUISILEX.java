package com.hbm.inventory.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerSILEX;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.SILEXRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.AuxButtonPacket;
import com.hbm.tileentity.machine.TileEntitySILEX;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUISILEX extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_silex.png");
	private TileEntitySILEX silex;
	int offset = 0;

	public GUISILEX(InventoryPlayer invPlayer, TileEntitySILEX laser) {
		super(new ContainerSILEX(invPlayer, laser));
		this.silex = laser;

		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		silex.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 42, 52, 7);
		
		if(silex.current != null) {
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 27, guiTop + 72, 16, 52, mouseX, mouseY, new String[] { silex.currentFill + "/" + silex.maxFill + "mB", silex.current.toStack().getDisplayName() });
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 10, guiTop + 92, 10, 10, mouseX, mouseY, new String[] { "Void contents" });
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 10 <= x && guiLeft + 10 + 12 > x && guiTop + 92 < y && guiTop + 92 + 12 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(silex.xCoord, silex.yCoord, silex.zCoord, 0, 0));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.silex.hasCustomInventoryName() ? this.silex.getInventoryName() : I18n.format(this.silex.getInventoryName());

		this.fontRendererObj.drawString(name, (this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2) - 54, 8, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		if(silex.mode != EnumWavelengths.NULL) {
			this.fontRendererObj.drawString(silex.mode.textColor + I18nUtil.resolveKey(silex.mode.name), 100 + (32 - this.fontRendererObj.getStringWidth(I18nUtil.resolveKey(silex.mode.name)) / 2), 16, 0);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(silex.mode != EnumWavelengths.NULL) {
			float freq = 0.1F * (float)Math.pow(2, silex.mode.ordinal());
			int color = (silex.mode != EnumWavelengths.VISIBLE) ? silex.mode.guiColor : Color.HSBtoRGB(silex.getWorldObj().getTotalWorldTime() / 50.0F, 0.5F, 1F) & 16777215;
			drawWave(81, 46, 16, 84, 0.5F, freq, color, 3F, 1F);
		}
		
		if(silex.tank.getFill() > 0) {
			
			if(silex.tank.getTankType() == Fluids.PEROXIDE || silex.fluidConversion.containsKey(silex.tank.getTankType()) || SILEXRecipes.getOutput(new ItemStack(ModItems.fluid_icon, 1, silex.tank.getTankType().getID())) != null) {
				drawTexturedModalRect(guiLeft + 7, guiTop + 41, 176, 118, 54, 9);
			} else {
				drawTexturedModalRect(guiLeft + 7, guiTop + 41, 176, 109, 54, 9);
			}
		}

		int p = silex.getProgressScaled(69);
		drawTexturedModalRect(guiLeft + 45, guiTop + 82, 176, 0, p, 43);

		int f = silex.getFillScaled(52);
		drawTexturedModalRect(guiLeft + 26, guiTop + 124 - f, 176, 109 - f, 16, f);

		int i = silex.getFluidScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 42, 176, silex.tank.getTankType() == Fluids.PEROXIDE ? 43 : 50, i, 7);
	}
	
	private void drawWave(int x, int y, int height, int width, float resolution, float freq, int color, float thickness, float mult) {
		float samples = ((float)width) / resolution;
		float scale = ((float)height)/2F;
		float offset = (float)((float)silex.getWorldObj().getTotalWorldTime() % (4*Math.PI/freq));//((width/3)*Math.PI/3));//(2.05F*width*freq));
		for(int i = 1; i < samples; i++) {
			double currentX = offset + x + i*resolution;
			double nextX = offset + x + (i+1)*resolution;
			double currentY = y + scale*Math.sin(freq*currentX);
			double nextY = y + scale*Math.sin(freq*nextX);
			drawLine(thickness, color, currentX-offset, currentY, nextX-offset, nextY);
			
			
		}
	}
	
	private void drawLine(float width, int color, double x1, double y1, double x2, double y2) {
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glLineWidth(width);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(1);
		tessellator.setColorOpaque_I(color);
		
		tessellator.addVertex(guiLeft + x1, guiTop + y1, this.zLevel);
		tessellator.addVertex(guiLeft + x2, guiTop + y2, this.zLevel);
		tessellator.draw();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}
}