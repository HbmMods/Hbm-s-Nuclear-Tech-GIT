package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.imc.ICompatNHNEI;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.gui.GUIBook;
import com.hbm.inventory.recipes.MagicRecipes;
import com.hbm.inventory.recipes.MagicRecipes.MagicRecipe;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.hbm.items.ModItems;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class BookRecipeHandler extends TemplateRecipeHandler implements ICompatNHNEI {
	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[]{
				new ItemStack(ModItems.book_of_)};
	}

	@Override
	public String getRecipeID() {
		return "book_of_boxcars";
	}
	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
    	
    	List<PositionedStack> input;
        PositionedStack result;
    	
        public RecipeSet(List<Object> in, ItemStack result) {
        	
        	input = new ArrayList();
        	
        	for(int i = 0; i < Math.min(in.size(), 4); i++) {
        		input.add(new PositionedStack(in.get(i), 25 + (i % 2) * 36, 6 + (i / 2) * 36));
        	}
        	
            this.result = new PositionedStack(result, 119, 24);
        }

        @Override
		public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, input);
        }

        @Override
		public PositionedStack getResult() {
            return result;
        }
    }
    
	@Override
	public String getRecipeName() {
		return "Black Book";
	}

	@Override
	public String getGuiTexture() {
		return GUIBook.texture.toString();
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if ((outputId.equals("book_of_boxcars")) && getClass() == BookRecipeHandler.class) {
			
			List<MagicRecipe> recipes = MagicRecipes.getRecipes();
			
			for(MagicRecipe recipe : recipes) {
				
				List<Object> input = new ArrayList();
				
				for(AStack stack : recipe.in) {
					
					if(stack instanceof ComparableStack)
						input.add(((ComparableStack)stack).toStack());
					
					if(stack instanceof OreDictStack)
						input.add(((OreDictStack)stack).toStacks());
				}
				
				this.arecipes.add(new RecipeSet(input, recipe.out));
			}
			
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		List<MagicRecipe> recipes = MagicRecipes.getRecipes();
		
		for (MagicRecipe recipe : recipes) {
			
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.out, result)) {
				
				List<Object> input = new ArrayList();
				
				for(AStack stack : recipe.in) {
					
					if(stack instanceof ComparableStack)
						input.add(((ComparableStack)stack).toStack());
					
					if(stack instanceof OreDictStack)
						input.add(((OreDictStack)stack).toStacks());
				}
				
				this.arecipes.add(new RecipeSet(input, recipe.out));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if ((inputId.equals("book_of_boxcars")) && getClass() == BookRecipeHandler.class) {
			
			loadCraftingRecipes("book_of_boxcars", new Object[0]);
			
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		List<MagicRecipe> recipes = MagicRecipes.getRecipes();
		
		for (MagicRecipe recipe : recipes) {
			
			for(AStack astack : recipe.in) {
				
				if(astack.isApplicable(ingredient)) {
					
					List<Object> input = new ArrayList();
					
					for(AStack stack : recipe.in) {
						
						if(stack instanceof ComparableStack)
							input.add(((ComparableStack)stack).toStack());
						
						if(stack instanceof OreDictStack)
							input.add(((OreDictStack)stack).toStacks());
					}
					
					this.arecipes.add(new RecipeSet(input, recipe.out));
					break;
				}
			}
		}
	}
    
    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(84, 34 - 11, 24, 18), "book_of_boxcars"));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GUIBook.class;
    }
}
