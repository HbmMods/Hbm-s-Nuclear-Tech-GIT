package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.ComparableNBTStack;
import com.hbm.inventory.gui.GUIMachineAssembler;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class AssemblerRecipeHandler extends TemplateRecipeHandler {
	
    public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
    public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
    public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
    public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe {
    	
		List<PositionedStack> input;
        PositionedStack result;
    	
        public SmeltingSet(List<Object> in, ItemStack result) {
        	
        	input = new ArrayList();
        	ComparableStack comp;
        	if(result.hasTagCompound())
        		comp = new ComparableNBTStack(result).addNBT(result.getTagCompound());
        	else
        		comp = new ComparableStack(result);
        	ItemStack template = new ItemStack(ModItems.assembly_template, 1, AssemblerRecipes.recipeList.indexOf(comp));
        	
        	for(int i = 0; i < Math.min(in.size(), 17); i++) {
        		if(i < 15)
        			input.add(new PositionedStack(in.get(i), 22 + (i % 5) * 18, 6 + (i / 5) * 18));
        		else
        			input.add(new PositionedStack(in.get(i), 22 + (i % 10) * 18, 6 + (i / 5) * 18));
        	}
            
            input.add(new PositionedStack(template, 122, 4));
            this.result = new PositionedStack(result, 149, 24);
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
		return "Assembly Machine";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_assembler.png";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if ((outputId.equals("assembly")) && getClass() == AssemblerRecipeHandler.class) {
			
			Map<ItemStack, List<Object>> recipes = AssemblerRecipes.getRecipes();
			
			for (Map.Entry<ItemStack, List<Object>> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet(recipe.getValue(), recipe.getKey()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		Map<ItemStack, List<Object>> recipes = AssemblerRecipes.getRecipes();
		
		for (Map.Entry<ItemStack, List<Object>> recipe : recipes.entrySet()) {
			
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), result))
				this.arecipes.add(new SmeltingSet(recipe.getValue(), recipe.getKey()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if ((inputId.equals("assembly")) && getClass() == AssemblerRecipeHandler.class) {
			loadCraftingRecipes("assembly", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		Map<ItemStack, List<Object>> recipes = AssemblerRecipes.getRecipes();
		
		for (Map.Entry<ItemStack, List<Object>> recipe : recipes.entrySet()) {
			
			for(Object o : recipe.getValue()) {
				
				if(o instanceof ItemStack && NEIServerUtils.areStacksSameTypeCrafting((ItemStack)o, ingredient)) {
					this.arecipes.add(new SmeltingSet(recipe.getValue(), recipe.getKey()));
					
				} else if(o instanceof List) {
					
					for(Object obj : (List)o) {
						
						if(obj instanceof ItemStack && NEIServerUtils.areStacksSameTypeCrafting((ItemStack)obj, ingredient)) {
							this.arecipes.add(new SmeltingSet(recipe.getValue(), recipe.getKey()));
						}
					}
				}
			}
		}
	}

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
    	return null;
    }
    
    @Override
    public void loadTransferRects() {
        transferRectsGui = new LinkedList<RecipeTransferRect>();
        guiGui = new LinkedList<Class<? extends GuiContainer>>();

        transferRects.add(new RecipeTransferRect(new Rectangle(112, 23, 36, 18), "assembly"));
//        transferRectsGui.add(new RecipeTransferRect(new Rectangle(18 * 2 + 2, 89 - 7 - 11, 18 * 5 - 4, 18 + 16), "assembly"));
        transferRectsGui.add(new RecipeTransferRect(new Rectangle(18 * 6 + 2, 89 - 7 - 11, 18 * 5 - 4, 18 + 16), "assembly"));
        guiGui.add(GUIMachineAssembler.class);
        RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
        RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
    }

    @Override
    public void drawExtras(int recipe) {

    	drawProgressBar(83 - (18 * 4) - 9 + 1, 6, 0, 86, 16, 18 * 3 - 2, 480, 7);
        
        drawProgressBar(83 - 3 + 16 + 5 + 10, 5 + 16, 16, 86, 36, 18, 48, 0);
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return super.newInstance();
    }

}
