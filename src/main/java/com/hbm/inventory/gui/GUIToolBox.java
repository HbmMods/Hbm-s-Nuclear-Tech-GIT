package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerToolBox;
import com.hbm.lib.RefStrings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static com.hbm.items.tool.ItemToolBox.*;

public class GUIToolBox extends GuiContainer {

	private final static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_toolbox.png");
	private final InventoryToolBox inventory;
	private ItemStack firstHeld;

	public GUIToolBox(InventoryPlayer invPlayer, InventoryToolBox box) {
		super(new ContainerToolBox(invPlayer, box));
		this.inventory = box;

		this.xSize = 176;
		this.ySize = 211;
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

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 37, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
