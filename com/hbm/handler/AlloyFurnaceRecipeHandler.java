package com.hbm.handler;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.gui.MachineRecipes;
import com.hbm.gui.gui.GUITestDiFurnace;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.FurnaceRecipeHandler.FuelPair;
import codechicken.nei.recipe.FurnaceRecipeHandler.SmeltingPair;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class AlloyFurnaceRecipeHandler extends TemplateRecipeHandler {

    public static ArrayList<Fuel> fuels;

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe
    {
    	PositionedStack input1;
		PositionedStack input2;
        PositionedStack result;
    	
        public SmeltingSet(ItemStack input1, ItemStack input2, ItemStack result) {
        	input1.stackSize = 1;
        	input2.stackSize = 1;
            this.input1 = new PositionedStack(input1, 75, 7);
            this.input2 = new PositionedStack(input2, 75, 43);
            this.result = new PositionedStack(result, 129, 25);
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] {input1, input2}));
        }

        public PositionedStack getOtherStack() {
            return fuels.get((cycleticks / 48) % fuels.size()).stack;
        }

        public PositionedStack getResult() {
            return result;
        }
    }

    public static class Fuel
    {
        public Fuel(ItemStack ingred) {
        	
            this.stack = new PositionedStack(ingred, 3, 25, false);
        }

        public PositionedStack stack;
    }
    
	@Override
	public String getRecipeName() {
		return "Alloy Furnace";
	}

	@Override
	public String getGuiTexture() {
		return GUITestDiFurnace.texture.toString();
	}
	
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("alloysmelting")) && getClass() == AlloyFurnaceRecipeHandler.class) {
			Map<Object[], Object> recipes = MachineRecipes.instance().getAlloyRecipes();
			for (Map.Entry<Object[], Object> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey()[0], (ItemStack)recipe.getKey()[1], (ItemStack)recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	public void loadCraftingRecipes(ItemStack result) {
		Map<Object[], Object> recipes = MachineRecipes.instance().getAlloyRecipes();
		for (Map.Entry<Object[], Object> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType((ItemStack)recipe.getValue(), result))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey()[0], (ItemStack)recipe.getKey()[1], (ItemStack)recipe.getValue()));
		}
	}

	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("alloysmelting")) && getClass() == AlloyFurnaceRecipeHandler.class) {
			loadCraftingRecipes("alloysmelting", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object[], Object> recipes = MachineRecipes.instance().getAlloyRecipes();
		for (Map.Entry<Object[], Object> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType(ingredient, (ItemStack)recipe.getKey()[0]) || NEIServerUtils.areStacksSameType(ingredient, (ItemStack)recipe.getKey()[1]))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey()[0], (ItemStack)recipe.getKey()[1], (ItemStack)recipe.getValue()));				
		}
	}

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GUITestDiFurnace.class;
    }
    
    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(96, 25, 24, 18), "alloysmelting"));
    }

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(57, 26, 176, 0, 14, 14, 48, 7);
        
        drawProgressBar(96, 24, 176, 14, 24, 16, 48, 0);

        drawProgressBar(39, 7, 201, 0, 16, 52, 480, 7);
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        if (fuels == null || fuels.isEmpty())
            fuels = new ArrayList<Fuel>();
        for(ItemStack i : MachineRecipes.instance().getAlloyFuels())
        {
        	fuels.add(new Fuel(i));
        }
        return super.newInstance();
    }

}
