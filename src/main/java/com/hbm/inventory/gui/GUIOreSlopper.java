package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerOreSlopper;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineOreSlopper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIOreSlopper extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_ore_slopper.png");
	private TileEntityMachineOreSlopper slopper;

	public GUIOreSlopper(InventoryPlayer player, TileEntityMachineOreSlopper slopper) {
		super(new ContainerOreSlopper(player, slopper));
		this.slopper = slopper;
		
		this.xSize = 176;
		this.ySize = 204;
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		
		String name = this.slopper.hasCustomInventoryName() ? this.slopper.getInventoryName() : I18n.format(this.slopper.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
