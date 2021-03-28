package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.FusionRecipes;
import com.hbm.inventory.gui.GUIITER;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class FusionRecipeHandler extends TemplateRecipeHandler {
	
    public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
    public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
    public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
    public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe {
    	
		PositionedStack input;
        PositionedStack result;
    	
        public SmeltingSet(ItemStack in, ItemStack out) {
        	
        	this.input = new PositionedStack(in, 30, 24);
            this.result = new PositionedStack(out, 120, 24);
        }

        @Override
		public List<PositionedStack> getIngredients() {
        	
            return new ArrayList() {{ add(input); }};
        }

        @Override
		public PositionedStack getResult() {
            return result;
        }
    }
    
	@Override
	public String getRecipeName() {
		return "Fusion Reactor";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals("fusion") && getClass() == FusionRecipeHandler.class) {
			
			Map<ItemStack, ItemStack> recipes = FusionRecipes.getRecipes();
			
			for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet(recipe.getKey(), recipe.getValue()));
			}
			
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		Map<ItemStack, ItemStack> recipes = FusionRecipes.getRecipes();
		
		for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
			
			if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue(), result)) {
				this.arecipes.add(new SmeltingSet(recipe.getKey(), recipe.getValue()));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("fusion") && getClass() == FusionRecipeHandler.class) {
			loadCraftingRecipes("fusion", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		Map<ItemStack, ItemStack> recipes = FusionRecipes.getRecipes();
		
		for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
			
			if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
				this.arecipes.add(new SmeltingSet(recipe.getKey(), recipe.getValue()));
			}
		}
	}
    
    @Override
    public void loadTransferRects() {
        transferRectsGui = new LinkedList<RecipeTransferRect>();
        guiGui = new LinkedList<Class<? extends GuiContainer>>();

        transferRects.add(new RecipeTransferRect(new Rectangle(52 - 5, 34 - 11, 18 * 4, 18), "fusion"));
        transferRectsGui.add(new RecipeTransferRect(new Rectangle(115 - 5, 17 - 11, 18, 18), "fusion"));
        guiGui.add(GUIITER.class);
        RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
        RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
    }

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_fusion.png";
	}
}
