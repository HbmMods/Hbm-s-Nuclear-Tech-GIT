package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.MachineRecipes;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class FluidRecipeHandler extends TemplateRecipeHandler {

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe
    {
    	PositionedStack input;
        PositionedStack result;
    	
        public SmeltingSet(ItemStack input, ItemStack result) {
        	input.stackSize = 1;
            this.input = new PositionedStack(input, 83 - 27 - 18 + 1, 5 + 18 + 1);
            this.result = new PositionedStack(result, 83 + 27 + 18 + 1 - 18, 5 + 18 + 1);
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
		return "Fluid Containers";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_fluid.png";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("fluidcons")) && getClass() == FluidRecipeHandler.class) {
			Map<Object, Object> recipes = MachineRecipes.instance().getFluidContainers();
			for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object, Object> recipes = MachineRecipes.instance().getFluidContainers();
		for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType((ItemStack)recipe.getValue(), result) || compareFluidStacks(result, (ItemStack)recipe.getKey()))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("fluidcons")) && getClass() == FluidRecipeHandler.class) {
			loadCraftingRecipes("fluidcons", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object, Object> recipes = MachineRecipes.instance().getFluidContainers();
		for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType((ItemStack)recipe.getValue(), ingredient) || compareFluidStacks(ingredient, (ItemStack)recipe.getKey()))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()));				
		}
	}
	
	private boolean compareFluidStacks(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        //return GUIMachineShredder.class;
    	return null;
    }
    
    @Override
    public void loadTransferRects() {
        
        transferRects.add(new RecipeTransferRect(new Rectangle(74 + 6 - 18, 23, 42, 18), "fluidcons"));
        RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
    }
}
