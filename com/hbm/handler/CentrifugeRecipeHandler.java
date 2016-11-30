package com.hbm.handler;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hbm.gui.MachineRecipes;
import com.hbm.gui.gui.GUIMachineCentrifuge;
import com.hbm.gui.gui.GUITestDiFurnace;
import com.hbm.handler.AlloyFurnaceRecipeHandler.Fuel;
import com.hbm.handler.AlloyFurnaceRecipeHandler.SmeltingSet;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class CentrifugeRecipeHandler extends TemplateRecipeHandler {

    public static ArrayList<Fuel> fuels;

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe
    {
    	PositionedStack input;
        PositionedStack result1;
        PositionedStack result2;
        PositionedStack result3;
        PositionedStack result4;
    	
        public SmeltingSet(ItemStack input, ItemStack result1, ItemStack result2, ItemStack result3, ItemStack result4) {
        	input.stackSize = 1;
            this.input = new PositionedStack(input, 21, 6);
            this.result1 = new PositionedStack(result1, 129, 6);
            this.result2 = new PositionedStack(result2, 147, 6);
            this.result3 = new PositionedStack(result3, 129, 42);
            this.result4 = new PositionedStack(result4, 147, 42);
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] {input}));
        }

        public List<PositionedStack> getOtherStacks() {
        	List<PositionedStack> stacks = new ArrayList<PositionedStack>();
            stacks.add(fuels.get((cycleticks / 48) % fuels.size()).stack);
            stacks.add(result2);
            stacks.add(result3);
            stacks.add(result4);
        	return stacks;
        }

        public PositionedStack getResult() {
            return result1;
        }
    }

    public static class Fuel
    {
        public Fuel(ItemStack ingred) {
        	
            this.stack = new PositionedStack(ingred, 21, 42, false);
        }

        public PositionedStack stack;
    }
    
	@Override
	public String getRecipeName() {
		return "Centrifuge";
	}

	@Override
	public String getGuiTexture() {
		return GUIMachineCentrifuge.texture.toString();
	}

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GUIMachineCentrifuge.class;
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        if (fuels == null || fuels.isEmpty())
            fuels = new ArrayList<Fuel>();
        for(ItemStack i : MachineRecipes.instance().getCentrifugeFuels())
        {
        	fuels.add(new Fuel(i));
        }
        return super.newInstance();
    }
	
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("centrifugeprocessing")) && getClass() == CentrifugeRecipeHandler.class) {
			Map<Object, Object[]> recipes = MachineRecipes.instance().getCentrifugeRecipes();
			for (Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()[0], (ItemStack)recipe.getValue()[1], (ItemStack)recipe.getValue()[2], (ItemStack)recipe.getValue()[3]));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	public void loadCraftingRecipes(ItemStack result) {
		Map<Object, Object[]> recipes = MachineRecipes.instance().getCentrifugeRecipes();
		for (Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType((ItemStack)recipe.getValue()[0], result) || NEIServerUtils.areStacksSameType((ItemStack)recipe.getValue()[1], result) || NEIServerUtils.areStacksSameType((ItemStack)recipe.getValue()[2], result) || NEIServerUtils.areStacksSameType((ItemStack)recipe.getValue()[3], result))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()[0], (ItemStack)recipe.getValue()[1], (ItemStack)recipe.getValue()[2], (ItemStack)recipe.getValue()[3]));
		}
	}

	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("centrifugeprocessing")) && getClass() == CentrifugeRecipeHandler.class) {
			loadCraftingRecipes("centrifugeprocessing", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object, Object[]> recipes = MachineRecipes.instance().getCentrifugeRecipes();
		for (Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType(ingredient, (ItemStack)recipe.getKey()))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()[0], (ItemStack)recipe.getValue()[1], (ItemStack)recipe.getValue()[2], (ItemStack)recipe.getValue()[3]));				
		}
	}

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(21, 24, 195, 55, 16, 16, 48, 7);
        
        drawProgressBar(56, 5, 176, 0, 54, 54, 48 * 3, 0);

        drawProgressBar(3, 6, 177, 55, 16, 52, 480, 7);
    }
    
    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(56, 5, 54, 54), "centrifugeprocessing"));
    }

}
