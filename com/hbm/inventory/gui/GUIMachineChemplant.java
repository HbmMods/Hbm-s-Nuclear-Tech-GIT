package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineAssembler;
import com.hbm.inventory.container.ContainerMachineChemplant;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityMachineAssembler;
import com.hbm.tileentity.TileEntityMachineChemplant;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineChemplant extends GuiContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_chemplant.png");
	private TileEntityMachineChemplant assembler;
	
	public GUIMachineChemplant(InventoryPlayer invPlayer, TileEntityMachineChemplant tedf) {
		super(new ContainerMachineChemplant(invPlayer, tedf));
		assembler = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.assembler.hasCustomInventoryName() ? this.assembler.getInventoryName() : I18n.format(this.assembler.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = assembler.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 116, guiTop + 70 - i, 176, 52 - i, 16, i);

		int j = assembler.getProgressScaled(83);
		drawTexturedModalRect(guiLeft + 45, guiTop + 82, 2, 222, j, 32);
	}
}
