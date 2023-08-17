package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPWR;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.GaugeUtil;
import com.hbm.render.util.GaugeUtil.Gauge;
import com.hbm.tileentity.machine.TileEntityPWRController;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIPWR extends GuiInfoContainer {

	protected TileEntityPWRController controller;
	private final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_pwr.png");
	
	public GUIPWR(InventoryPlayer inventory, TileEntityPWRController controller) {
		super(new ContainerPWR(inventory, controller));
		this.controller = controller;
		
		this.xSize = 176;
		this.ySize = 188;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		this.drawCustomInfoStat(x, y, guiLeft + 115, guiTop + 31, 18, 18, x, y, new String[] { "Core: " + String.format("%,d", controller.coreHeat) + " / " + String.format("%,d", controller.coreHeatCapacity) + " TU" });
		this.drawCustomInfoStat(x, y, guiLeft + 151, guiTop + 31, 18, 18, x, y, new String[] { "Hull: " + String.format("%,d", controller.hullHeat) + " / " + String.format("%,d", controller.hullHeatCapacity) + " TU" });
		
		//TODO: calculate some prediction using extrapolation (or some math sector that ends with -ic)
		//int timeLeft = (controller.processTime - controller.progress) / 20;
		//this.drawCustomInfoStat(x, y, guiLeft + 52, guiTop + 31, 36, 18, x, y, new String[] { "Cycle: " + (timeLeft / 60) + ":" + String.format("%02d", timeLeft % 60)});

		
		if(controller.typeLoaded != -1 && controller.amountLoaded > 0) {
			ItemStack display = new ItemStack(ModItems.pwr_fuel, 1, controller.typeLoaded);
			if(guiLeft + 88 <= x && guiLeft + 88 + 18 > x && guiTop + 4 < y && guiTop + 4 + 18 >= y) this.renderToolTip(display, x, y);
		}
		
		controller.tanks[0].renderTankInfo(this, x, y, guiLeft + 8, guiTop + 5, 16, 52);
		controller.tanks[1].renderTankInfo(this, x, y, guiLeft + 26, guiTop + 5, 16, 52);
	}

	@Override
	protected void drawItemStack(ItemStack stack, int x, int y, String label) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		this.zLevel = 200.0F;
		itemRender.zLevel = 200.0F;
		FontRenderer font = null;
		if(stack != null) font = stack.getItem().getFontRenderer(stack);
		if(font == null) font = fontRendererObj;
		itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, x, y);
		GL11.glScaled(0.5, 0.5, 0.5);
		itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), stack, (x + font.getStringWidth(label) / 4) * 2, (y + 15) * 2, label);
		this.zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
		GL11.glPopMatrix();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		double scale = 1.25;
		String flux = String.format("%,.1f", controller.flux);
		GL11.glScaled(1 / scale, 1 / scale, 1);
		this.fontRendererObj.drawString(flux, (int) (165 * scale - this.fontRendererObj.getStringWidth(flux)), (int)(64 * scale), 0x00ff00);
		GL11.glScaled(scale, scale, 1);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(System.currentTimeMillis() % 1000 < 500)
			drawTexturedModalRect(guiLeft + 147, guiTop, 176, 14, 26, 26);

		GaugeUtil.renderGauge(Gauge.ROUND_SMALL, guiLeft + 115, guiTop + 31, this.zLevel, (double) controller.coreHeat / (double) controller.coreHeatCapacity);
		GaugeUtil.renderGauge(Gauge.ROUND_SMALL, guiLeft + 151, guiTop + 31, this.zLevel, (double) controller.hullHeat / (double) controller.hullHeatCapacity);
		
		if(controller.typeLoaded != -1 && controller.amountLoaded > 0) {
			ItemStack display = new ItemStack(ModItems.pwr_fuel, 1, controller.typeLoaded);
			this.drawItemStack(display, guiLeft + 89, guiTop + 5, EnumChatFormatting.YELLOW + "" + controller.amountLoaded + "/" + controller.rodCount);
			RenderHelper.enableGUIStandardItemLighting();
		}
	}
}
