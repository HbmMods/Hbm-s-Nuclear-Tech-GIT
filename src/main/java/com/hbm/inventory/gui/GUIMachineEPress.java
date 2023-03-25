package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineEPress;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineEPress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineEPress extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_epress.png");
	private TileEntityMachineEPress press;
	
	public GUIMachineEPress(InventoryPlayer invPlayer, TileEntityMachineEPress tedf) {
		super(new ContainerMachineEPress(invPlayer, tedf));
		press = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 17, guiTop + 69 - 52, 16, 52, press.power, press.maxPower);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.press.hasCustomInventoryName() ? this.press.getInventoryName() : I18n.format(this.press.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int) (press.power * 52 / press.maxPower);
		drawTexturedModalRect(guiLeft + 17, guiTop + 69 - i, 176, 52 - i, 16, i);
		
		int k = (int) (press.renderPress * 16 / press.maxPress);
		this.drawTexturedModalRect(guiLeft + 79, guiTop + 35, 192, 0, 18, k);
	}
}
