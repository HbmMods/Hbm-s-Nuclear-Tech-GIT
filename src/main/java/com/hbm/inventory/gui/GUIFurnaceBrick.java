package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerFurnaceBrick;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFurnaceBrick;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIFurnaceBrick extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_furnace_brick.png");
	private TileEntityFurnaceBrick furnace;

	public GUIFurnaceBrick(InventoryPlayer invPlayer, TileEntityFurnaceBrick tile) {
		super(new ContainerFurnaceBrick(invPlayer, tile));
		this.furnace = tile;
	}

	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		String name = this.furnace.hasCustomInventoryName() ? this.furnace.getInventoryName() : I18n.format(this.furnace.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0xffffff);
	}

	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
		
		if(furnace.isInvalid() && furnace.getWorldObj().getTileEntity(furnace.xCoord, furnace.yCoord, furnace.zCoord) instanceof TileEntityFurnaceBrick)
			furnace = (TileEntityFurnaceBrick) furnace.getWorldObj().getTileEntity(furnace.xCoord, furnace.yCoord, furnace.zCoord);

		if(this.furnace.burnTime > 0) {
			int b = furnace.burnTime * 13 / furnace.maxBurnTime;
			this.drawTexturedModalRect(guiLeft + 62, guiTop + 54 + 12 - b, 176, 12 - b, 14, b + 1);
			int p = this.furnace.progress * 24 / 200;
			this.drawTexturedModalRect(guiLeft + 85, guiTop + 34, 176, 14, p + 1, 16);
		}
	}
}
