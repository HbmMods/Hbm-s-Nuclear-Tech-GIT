package com.hbm.handler;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.gui.GUIMachineReactor;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class ReactorRecipeHandler extends TemplateRecipeHandler {

    public static ArrayList<Fuel> fuels;

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe
    {
    	PositionedStack input;
        PositionedStack result;
    	
        public SmeltingSet(ItemStack input, ItemStack result) {
        	input.stackSize = 1;
            this.input = new PositionedStack(input, 51, 6);
            this.result = new PositionedStack(result, 111, 24);
        }

        @Override
		public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] {input}));
        }

        @Override
		public PositionedStack getOtherStack() {
            return fuels.get((cycleticks / 48) % fuels.size()).stack;
        }

        @Override
		public PositionedStack getResult() {
            return result;
        }
    }

    public static class Fuel
    {
        public Fuel(ItemStack ingred) {
        	
            this.stack = new PositionedStack(ingred, 51, 42, false);
        }

        public PositionedStack stack;
    }
    
	@Override
	public String getRecipeName() {
		return "Breeding Reactor";
	}

	@Override
	public String getGuiTexture() {
		return GUIMachineReactor.texture.toString();
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("breeding")) && getClass() == ReactorRecipeHandler.class) {
			Map<Object, Object> recipes = MachineRecipes.instance().getReactorRecipes();
			for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object, Object> recipes = MachineRecipes.instance().getReactorRecipes();
		for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType((ItemStack)recipe.getValue(), result))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("breeding")) && getClass() == ReactorRecipeHandler.class) {
			loadCraftingRecipes("breeding", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object, Object> recipes = MachineRecipes.instance().getReactorRecipes();
		for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType(ingredient, (ItemStack)recipe.getKey()))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()));				
		}
	}

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GUIMachineReactor.class;
    }
    
    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "breeding"));
    }

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(50, 24, 176, 0, 14, 14, 48 * 3, 7);
        drawProgressBar(74, 23, 176, 16, 24, 16, 48, 0);
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        if (fuels == null || fuels.isEmpty())
            fuels = new ArrayList<Fuel>();
        for(ItemStack i : MachineRecipes.instance().getReactorFuels())
        {
        	fuels.add(new Fuel(i));
        }
        return super.newInstance();
    }
}
