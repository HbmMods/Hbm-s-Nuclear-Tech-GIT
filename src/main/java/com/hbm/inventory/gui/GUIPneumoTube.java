package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPneumoTube;
import com.hbm.lib.RefStrings;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.render.util.GaugeUtil;
import com.hbm.tileentity.network.TileEntityPneumoTube;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIPneumoTube extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_pneumatic_pipe.png");
	public TileEntityPneumoTube tube;

	public GUIPneumoTube(InventoryPlayer invPlayer, TileEntityPneumoTube tedf) {
		super(new ContainerPneumoTube(invPlayer, tedf));
		this.tube = tedf;
		
		this.xSize = 176;
		this.ySize = 185;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		tube.compair.renderTankInfo(this, x, y, guiLeft + 7, guiTop + 16, 18, 18);

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for(int i = 0; i < 15; ++i) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
	
				if(this.isMouseOverSlot(slot, x, y) && tube.pattern.modes[i] != null) {
					this.func_146283_a(Arrays.asList(new String[] { EnumChatFormatting.RED + "Right click to change", ModulePatternMatcher.getLabel(tube.pattern.modes[i]) }), x, y - 30);
				}
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.tube.hasCustomInventoryName() ? this.tube.getInventoryName() : I18n.format(this.tube.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		drawTexturedModalRect(guiLeft + 6 + 4 * (tube.compair.getPressure() - 1), guiTop + 36, 179, 18, 4, 8);
		GaugeUtil.drawSmoothGauge(guiLeft + 16, guiTop + 25, this.zLevel, (double) tube.compair.getFill() / (double) tube.compair.getMaxFill(), 5, 2, 1, 0xCA6C43, 0xAB4223);
	}
}
