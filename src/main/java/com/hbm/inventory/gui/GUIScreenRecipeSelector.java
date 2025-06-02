package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;

public class GUIScreenRecipeSelector extends GuiScreen {

	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_recipe_selector.png");
	
	//basic GUI setup
	protected int xSize = 176;
	protected int ySize = 132;
	protected int guiLeft;
	protected int guiTop;
	// search crap
	protected GenericRecipes recipeSet;
	protected List<GenericRecipe> recipes = new ArrayList();
	protected GuiTextField search;
	protected int pageIndex;
	protected int size;
	protected String selection;
	// callback
	protected int index;
	protected IControlReceiver tile;
	protected GuiScreen previousScreen;
	
	public static void openSelector(GenericRecipes recipeSet, IControlReceiver tile, String selection, int index, GuiScreen previousScreen) {
		FMLCommonHandler.instance().showGuiScreen(new GUIScreenRecipeSelector(recipeSet, tile, selection, index, previousScreen));
	}
	
	public GUIScreenRecipeSelector(GenericRecipes recipeSet, IControlReceiver tile, String selection, int index, GuiScreen previousScreen) {
		this.recipeSet = recipeSet;
		this.tile = tile;
		this.selection = selection;
		this.index = index;
		this.previousScreen = previousScreen;
		
		regenerateRecipes();
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		Keyboard.enableRepeatEvents(true);
		this.search = new GuiTextField(this.fontRendererObj, guiLeft + 28, guiTop + 111, 102, 12);
		this.search.setTextColor(-1);
		this.search.setDisabledTextColour(-1);
		this.search.setEnableBackgroundDrawing(false);
		this.search.setMaxStringLength(32);
	}
	
	private void regenerateRecipes() {
		
		this.recipes.clear();
		this.recipes.addAll(recipeSet.recipeOrderedList);
		
		resetPaging();
	}
	
	private void search(String search) {
		this.recipes.clear();
		
		if(search.isEmpty()) {
			this.recipes.addAll(recipeSet.recipeOrderedList);
		} else {
			for(Object o : recipeSet.recipeOrderedList) {
				GenericRecipe recipe = (GenericRecipe) o;
				if(recipe.matchesSearch(search)) this.recipes.add(recipe);
			}
		}
		
		resetPaging();
	}
	
	private void resetPaging() {
		this.pageIndex = 0;
		this.size = Math.max(0, (int)Math.ceil((this.recipes.size() - 40) / 8D));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
		this.handleScroll();
	}
	
	protected void handleScroll() {
		
		if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1) && Mouse.next()) {
			int scroll = Mouse.getEventDWheel();
			if(scroll > 0 && this.pageIndex > 0) this.pageIndex--;
			if(scroll < 0 && this.pageIndex < this.size) this.pageIndex++;
		}
	}

	private void drawGuiContainerForegroundLayer(int x, int y) {
		
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		for(int i = pageIndex * 8; i < pageIndex * 8 + 40; i++) {
			if(i >= recipes.size()) break;
			
			int ind = i - pageIndex * 8;
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			
			GenericRecipe recipe = recipes.get(i);
			
			FontRenderer font = recipe.getIcon().getItem().getFontRenderer(recipe.getIcon());
			if(font == null) font = fontRendererObj;
			
			itemRender.zLevel = 100.0F;
			itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), recipe.getIcon(), guiLeft + 8 + 18 * (ind % 8), guiTop + 18 + 18 * (ind / 8));

			itemRender.zLevel = 0.0F;

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.mc.getTextureManager().bindTexture(texture);
			
			if(recipe.name.equals(this.selection))
				this.drawTexturedModalRect(guiLeft + 7 + 18 * (ind % 8), guiTop + 17 + 18 * (ind / 8), 192, 0, 18, 18);
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		
		if(this.search.textboxKeyTyped(typedChar, keyCode)) {
			search(this.search.getText());
			return;
		}
			
		if(keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			FMLCommonHandler.instance().showGuiScreen(previousScreen);
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override public boolean doesGuiPauseGame() { return false; }
}
