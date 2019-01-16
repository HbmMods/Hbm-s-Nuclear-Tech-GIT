package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerLeadBox;
import com.hbm.inventory.container.ContainerWasteDrum;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemLeadBox;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityWasteDrum;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GUILeadBox extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_containment.png");

	public GUILeadBox(InventoryPlayer invPlayer) {
		super(new ContainerLeadBox(invPlayer));
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = "";//ItemLeadBox.getGuiName();
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
