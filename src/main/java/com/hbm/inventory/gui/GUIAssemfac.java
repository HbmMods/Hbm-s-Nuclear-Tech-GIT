package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerAssemfac;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineAssemfac;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GUIAssemfac extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_assemfac.png");
	private TileEntityMachineAssemfac assemfac;

	public GUIAssemfac(InventoryPlayer invPlayer, TileEntityMachineAssemfac tedf) {
		super(new ContainerAssemfac(invPlayer, tedf));
		assemfac = tedf;
		
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
	protected void drawGuiContainerBackgroundLayer(float interp, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LMENU))
		for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++) {
			Slot s = this.inventorySlots.getSlot(i);

			this.fontRendererObj.drawStringWithShadow(i + "", guiLeft + s.xDisplayPosition + 2, guiTop + s.yDisplayPosition, 0xffffff);
			this.fontRendererObj.drawStringWithShadow(s.getSlotIndex() + "", guiLeft + s.xDisplayPosition + 2, guiTop + s.yDisplayPosition + 8, 0xff8080);
		}
	}
}
