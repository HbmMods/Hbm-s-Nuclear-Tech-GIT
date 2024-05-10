package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerICFPress;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityICFPress;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIICFPress extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_icf_press.png");
	private TileEntityICFPress press;

	public GUIICFPress(InventoryPlayer invPlayer, TileEntityICFPress tedf) {
		super(new ContainerICFPress(invPlayer, tedf));
		press = tedf;
		
		this.xSize = 176;
		this.ySize = 179;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		press.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 44, guiTop + 18, 16, 52);
		press.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 18, 16, 52);
		
		if(this.isMouseOverSlot(this.inventorySlots.getSlot(4), mouseX, mouseY) && !this.inventorySlots.getSlot(4).getHasStack()) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.YELLOW + "Item input: Top/Bottom"}), mouseX, mouseY);
		if(this.isMouseOverSlot(this.inventorySlots.getSlot(5), mouseX, mouseY) && !this.inventorySlots.getSlot(5).getHasStack()) this.func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.YELLOW + "Item input: Sides"}), mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.press.hasCustomInventoryName() ? this.press.getInventoryName() : I18n.format(this.press.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int m = press.muon * 52 / press.maxMuon;
		drawTexturedModalRect(guiLeft + 28, guiTop + 70 - m, 176, 52 - m, 4, m);

		press.tanks[0].renderTank(guiLeft + 44, guiTop + 70, this.zLevel, 16, 52);
		press.tanks[1].renderTank(guiLeft + 152, guiTop + 70, this.zLevel, 16, 52);
	}
}
