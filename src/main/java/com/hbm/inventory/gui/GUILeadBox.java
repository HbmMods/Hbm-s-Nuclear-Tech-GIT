package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerLeadBox;
import com.hbm.items.tool.ItemLeadBox.InventoryLeadBox;
import com.hbm.lib.RefStrings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUILeadBox extends GuiContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_containment.png");
	private final InventoryLeadBox inventory;
	private ItemStack firstHeld;

	public GUILeadBox(InventoryPlayer invPlayer, InventoryLeadBox box) {
		super(new ContainerLeadBox(invPlayer, box));
		this.inventory = box;

		this.xSize = 176;
		this.ySize = 186;
	}

	@Override
	public void drawScreen(int x, int y, float interp) {

		if(firstHeld == null) {
			// *very* unlikely to be incorrect on the first frame after opening, so doing this is good enough
			firstHeld = this.mc.thePlayer.getHeldItem();

		// if the open box has changed or disappeared, close the inventory
		} else if(this.mc.thePlayer.getHeldItem() != firstHeld) {
			//this.mc.thePlayer.closeScreen();
			//return;
		}

		super.drawScreen(x, y, interp);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.inventory.getInventoryName());

		if(inventory.hasCustomInventoryName()) {
			name = inventory.target.getDisplayName();
		}

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
