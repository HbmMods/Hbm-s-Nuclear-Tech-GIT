package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.gui.GUIMachineBoiler;
import com.hbm.inventory.gui.GUIMachineBoilerElectric;
import com.hbm.lib.RefStrings;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class BoilerRecipeHandler extends TemplateRecipeHandler {
	
    public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
    public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
    public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
    public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe
    {
    	PositionedStack input;
        PositionedStack result;
    	
        public SmeltingSet(ItemStack input, ItemStack result) {
        	input.stackSize = 1;
            this.input = new PositionedStack(input, 21 + 9, 6 + 18);
            this.result = new PositionedStack(result, 120, 24);
        }

        @Override
		public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] {input}));
        }

        @Override
		public PositionedStack getResult() {
            return result;
        }
    }
    
	@Override
	public String getRecipeName() {
		return "Boiler";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_boiler.png";
	}

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return null;
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return super.newInstance();
    }
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("ntmboiler")) && getClass() == BoilerRecipeHandler.class) {
			Map<Object, Object> recipes = MachineRecipes.instance().getBoilerRecipes();
			for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), 
						(ItemStack)recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object, Object> recipes = MachineRecipes.instance().getBoilerRecipes();
		for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			if (compareFluidStacks((ItemStack)recipe.getValue(), result) || 
					compareFluidStacks((ItemStack)recipe.getValue(), result))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), 
						(ItemStack)recipe.getValue()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("ntmboiler")) && getClass() == BoilerRecipeHandler.class) {
			loadCraftingRecipes("ntmboiler", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object, Object> recipes = MachineRecipes.instance().getBoilerRecipes();
		for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			if (compareFluidStacks(ingredient, (ItemStack)recipe.getKey()))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), 
						(ItemStack)recipe.getValue()));				
		}
	}
	
	private boolean compareFluidStacks(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(80, 23, 0, 85, 6, 17, 240, 3);
    }
    
    @Override
    public void loadTransferRects() {
        transferRectsGui = new LinkedList<RecipeTransferRect>();
        guiGui = new LinkedList<Class<? extends GuiContainer>>();

        transferRects.add(new RecipeTransferRect(new Rectangle(138 - 1 - 36 - 27 - 9, 23, 36, 18), "ntmboiler"));
        transferRectsGui.add(new RecipeTransferRect(new Rectangle(18 * 2 + 2 + 36, 89 - 29 - 18 - 18, 18, 18 * 2), "ntmboiler"));
        guiGui.add(GUIMachineBoiler.class);
        guiGui.add(GUIMachineBoilerElectric.class);
        RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
        RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
    }
}
