package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.PedestalRecipes;
import com.hbm.inventory.recipes.PedestalRecipes.PedestalRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenClayTablet extends GuiScreen {
	
	protected int xSize = 142;
	protected int ySize = 84;
	protected int guiLeft;
	protected int guiTop;
	protected int tabletMeta = 0;
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/guide_pedestal.png");
	
	public GUIScreenClayTablet() { }

	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	protected void drawGuiContainerForegroundLayer(int i, int j) { }

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(player.getHeldItem() != null) tabletMeta = player.getHeldItem().getItemDamage();
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		int tabletOffset = tabletMeta == 1 ? 84 : 0;
		int iconOffset = tabletMeta == 1 ? 16 : 0;
		float revealChance = tabletMeta == 1 ? 0.25F : 0.5F;
		drawTexturedModalRect(guiLeft, guiTop, 0,  tabletOffset, xSize, ySize);
		
		ArrayList<PedestalRecipe> recipeSet = PedestalRecipes.recipeSets[Math.abs(tabletMeta) % PedestalRecipes.recipeSets.length];
		
		if(player.getHeldItem() != null && player.getHeldItem().hasTagCompound() && player.getHeldItem().stackTagCompound.hasKey("tabletSeed") && !recipeSet.isEmpty()) {
			Random rand = new Random(player.getHeldItem().stackTagCompound.getLong("tabletSeed"));
			PedestalRecipe recipe = recipeSet.get(rand.nextInt(recipeSet.size()));

			if(recipe.extra == recipe.extra.FULL_MOON) drawTexturedModalRect(guiLeft + 120, guiTop + 62, 142 + iconOffset, 32, 16, 16);
			if(recipe.extra == recipe.extra.NEW_MOON) drawTexturedModalRect(guiLeft + 120, guiTop + 62, 142 + iconOffset, 48, 16, 16);
			if(recipe.extra == recipe.extra.SUN) drawTexturedModalRect(guiLeft + 120, guiTop + 62, 142 + iconOffset, 64, 16, 16);
			
			for(int l = 0; l < 3; l++) {
				for(int r = 0; r < 3; r++) {
					if(rand.nextFloat() > revealChance) {
						drawTexturedModalRect(guiLeft + 7 + r * 27, guiTop + 7 + l * 27, 142 + iconOffset, 16, 16, 16);
					} else {
						
						AStack ingredient = recipe.input[r + l * 3];
						
						if(ingredient == null) {
							drawTexturedModalRect(guiLeft + 7 + r * 27, guiTop + 7 + l * 27, 142 + iconOffset, 0, 16, 16);
							continue;
						}
						
						List<ItemStack> inputs = ingredient.extractForNEI();
						ItemStack input = inputs.size() <= 0 ? new ItemStack(ModItems.nothing) : inputs.get((int) (Math.abs(System.currentTimeMillis() / 1000) % inputs.size()));
						
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						RenderHelper.enableGUIStandardItemLighting();
						GL11.glEnable(GL12.GL_RESCALE_NORMAL);
						
						FontRenderer font = null;
						if(input != null) font = input.getItem().getFontRenderer(recipe.output);
						if(font == null) font = fontRendererObj;

						itemRender.zLevel = 300.0F;
						itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), input, guiLeft + 7 + r * 27, guiTop + 7 + l * 27);
						itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), input, guiLeft + 7 + r * 27, guiTop + 7 + l * 27, input.stackSize > 1 ? (input.stackSize + "") : null);
						itemRender.zLevel = 0.0F;

						GL11.glDisable(GL11.GL_LIGHTING);
						this.mc.getTextureManager().bindTexture(texture);
						this.zLevel = 300.0F;
					}
				}
			}
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			
			FontRenderer font = null;
			if(recipe.output != null) font = recipe.output.getItem().getFontRenderer(recipe.output);
			if(font == null) font = fontRendererObj;
			
			itemRender.zLevel = 300.0F;
			itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), recipe.output, guiLeft + xSize / 2 - 8, guiTop - 20);
			itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), recipe.output, guiLeft + xSize / 2 - 8, guiTop - 20, recipe.output.stackSize > 1 ? (recipe.output.stackSize + "") : null);
			itemRender.zLevel = 0.0F;
			
			GL11.glDisable(GL11.GL_LIGHTING);
			
			this.mc.getTextureManager().bindTexture(texture);
			this.zLevel = 300.0F;

			GL11.glDisable(GL11.GL_DEPTH_TEST);
			String label = recipe.output.getDisplayName();
			font.drawString(label, guiLeft + (xSize - font.getStringWidth(label)) / 2, guiTop - 30, 0xffffff);
			
		} else {
			
			for(int l = 0; l < 3; l++) {
				for(int r = 0; r < 3; r++) {
					drawTexturedModalRect(guiLeft + 7 + r * 27, guiTop + 7 + l * 27, 142 + iconOffset, 16, 16, 16);
				}
			}
		}
	}

	@Override
	protected void keyTyped(char c, int key) {
		if(key == 1 || key == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}
	}
	
	@Override public boolean doesGuiPauseGame() { return false; }
}
