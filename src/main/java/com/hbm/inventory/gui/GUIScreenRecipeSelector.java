package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GUIScreenRecipeSelector extends GuiScreen {

	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_recipe_selector.png");
	
	protected int xSize = 176;
	protected int ySize = 132;
	protected int guiLeft;
	protected int guiTop;
	
	protected GuiScreen previousScreen;
	
	public static void openSelector(GenericRecipes recipeSet, IControlReceiver tile, String selection, int index, GuiScreen previousScreen) {
		FMLCommonHandler.instance().showGuiScreen(new GUIScreenRecipeSelector(recipeSet, tile, selection, index, previousScreen));
	}
	
	public GUIScreenRecipeSelector(GenericRecipes recipeSet, IControlReceiver tile, String selection, int index, GuiScreen previousScreen) {
		this.previousScreen = previousScreen;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	private void drawGuiContainerForegroundLayer(int x, int y) {
		
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		if(keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			FMLCommonHandler.instance().showGuiScreen(previousScreen);
		}
	}

	@Override public boolean doesGuiPauseGame() { return false; }
}
