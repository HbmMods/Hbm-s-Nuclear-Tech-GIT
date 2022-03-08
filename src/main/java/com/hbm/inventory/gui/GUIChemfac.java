package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerChemfac;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineChemfac;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GUIChemfac extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_chemfac.png");
	private TileEntityMachineChemfac chemfac;

	public GUIChemfac(InventoryPlayer invPlayer, TileEntityMachineChemfac tedf) {
		super(new ContainerChemfac(invPlayer, tedf));
		chemfac = tedf;
		
		this.xSize = 256;
		this.ySize = 256;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) { }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, 211);
		drawTexturedModalRect(guiLeft + 26, guiTop + 211, 26, 211, 176, 45);
		
		for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++) {
			Slot s = this.inventorySlots.getSlot(i);

			this.fontRendererObj.drawStringWithShadow(i + "", guiLeft + s.xDisplayPosition + 2, guiTop + s.yDisplayPosition, 0xffffff);
			this.fontRendererObj.drawStringWithShadow(s.getSlotIndex() + "", guiLeft + s.xDisplayPosition + 2, guiTop + s.yDisplayPosition + 8, 0xff8080);
		}
	}
}
