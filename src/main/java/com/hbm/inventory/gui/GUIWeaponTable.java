package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerWeaponTable;
import com.hbm.lib.RefStrings;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIWeaponTable extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_armor_modifier.png");
	public int left;
	public int top;

	public GUIWeaponTable(InventoryPlayer player) {
		super(new ContainerWeaponTable(player));

		this.xSize = 176;
		this.ySize = 240;

		guiLeft = (this.width - this.xSize) / 2;
		guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mX, int mY) {

		String name = I18n.format("container.armorTable");
		this.fontRendererObj.drawString(name, (this.xSize - 22) / 2 - this.fontRendererObj.getStringWidth(name) / 2 + 22, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8 + 22, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float inter, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
	}
}
