package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCoreReceiver;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityCoreReceiver;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICoreReceiver extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/dfc/gui_receiver.png");
	private TileEntityCoreReceiver receiver;
	
	public GUICoreReceiver(InventoryPlayer invPlayer, TileEntityCoreReceiver tedf) {
		super(new ContainerCoreReceiver(invPlayer, tedf));
		receiver = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		receiver.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 17, 16, 52);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.receiver.hasCustomInventoryName() ? this.receiver.getInventoryName() : I18n.format(this.receiver.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);

		this.fontRendererObj.drawString("Input:", 40, 25, 0xFF7F7F);
		this.fontRendererObj.drawString(BobMathUtil.getShortNumber(receiver.joules) + "Spk", 50, 35, 0xFF7F7F);
		this.fontRendererObj.drawString("Output:", 40, 45, 0xFF7F7F);
		this.fontRendererObj.drawString(BobMathUtil.getShortNumber(receiver.joules * 5000) + "HE", 50, 55, 0xFF7F7F);
		
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		receiver.tank.renderTank(guiLeft + 8, guiTop + 69, this.zLevel, 16, 52);
	}
}
