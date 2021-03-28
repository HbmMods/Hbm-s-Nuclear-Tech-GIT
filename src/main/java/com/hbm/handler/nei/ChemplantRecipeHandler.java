package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.gui.GUIMachineChemplant;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class ChemplantRecipeHandler extends TemplateRecipeHandler {
	
    public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
    public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
    public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
    public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe
    {
    	PositionedStack input1;
		PositionedStack input2;
		PositionedStack input3;
		PositionedStack input4;
		PositionedStack inputF1;
		PositionedStack inputF2;
		PositionedStack output1;
		PositionedStack output2;
		PositionedStack output3;
		PositionedStack output4;
		PositionedStack outputF1;
		PositionedStack outputF2;
		PositionedStack template;
    	
        public SmeltingSet(ItemStack inputF1, ItemStack inputF2, ItemStack input1,
        		ItemStack input2, ItemStack input3, ItemStack input4, ItemStack outputF1,
        		ItemStack outputF2, ItemStack output1, ItemStack output2, ItemStack output3,
        		ItemStack output4, ItemStack template) {
            this.inputF1 = new PositionedStack(inputF1, 30, 6);
            this.inputF2 = new PositionedStack(inputF2, 30 + 18, 6);
            this.input1 = new PositionedStack(input1, 30, 6 + 18);
            this.input2 = new PositionedStack(input2, 30 + 18, 6 + 18);
            this.input3 = new PositionedStack(input3, 30, 6 + 36);
            this.input4 = new PositionedStack(input4, 30 + 18, 6 + 36);
            this.outputF1 = new PositionedStack(outputF1, 120, 6);
            this.outputF2 = new PositionedStack(outputF2, 120 + 18, 6);
            this.output1 = new PositionedStack(output1, 120, 6 + 18);
            this.output2 = new PositionedStack(output2, 120 + 18, 6 + 18);
            this.output3 = new PositionedStack(output3, 120, 6 + 36);
            this.output4 = new PositionedStack(output4, 120 + 18, 6 + 36);
            this.template = new PositionedStack(template, 84, 6);
        }

        @Override
		public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] {input1, input2, input3, input4, inputF1, inputF2, template}));
        }

        @Override
		public List<PositionedStack> getOtherStacks() {
        	List<PositionedStack> stacks = new ArrayList<PositionedStack>();
            stacks.add(output1);
            stacks.add(output2);
            stacks.add(output3);
            stacks.add(output4);
            stacks.add(outputF1);
            stacks.add(outputF2);
        	return stacks;
        }

        @Override
		public PositionedStack getResult() {
            return output1;
        }
    }
    
	@Override
	public String getRecipeName() {
		return "Chemical Plant";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_chemplant.png";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("chemistry")) && getClass() == ChemplantRecipeHandler.class) {
			Map<Object[], Object[]> recipes = MachineRecipes.instance().getChemistryRecipes();
			for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet(
						(ItemStack)recipe.getKey()[0],
						(ItemStack)recipe.getKey()[1],
						(ItemStack)recipe.getKey()[2],
						(ItemStack)recipe.getKey()[3],
						(ItemStack)recipe.getKey()[4],
						(ItemStack)recipe.getKey()[5],
						(ItemStack)recipe.getValue()[0],
						(ItemStack)recipe.getValue()[1],
						(ItemStack)recipe.getValue()[2],
						(ItemStack)recipe.getValue()[3],
						(ItemStack)recipe.getValue()[4],
						(ItemStack)recipe.getValue()[5],
						(ItemStack)recipe.getKey()[6]));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object[], Object[]> recipes = MachineRecipes.instance().getChemistryRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (compareFluidStacks(result, (ItemStack)recipe.getValue()[0]) || 
					compareFluidStacks(result, (ItemStack)recipe.getValue()[1]) || 
					NEIServerUtils.areStacksSameTypeCrafting(result, (ItemStack)recipe.getValue()[2]) || 
					NEIServerUtils.areStacksSameTypeCrafting(result, (ItemStack)recipe.getValue()[3]) || 
					NEIServerUtils.areStacksSameTypeCrafting(result, (ItemStack)recipe.getValue()[4]) || 
					NEIServerUtils.areStacksSameTypeCrafting(result, (ItemStack)recipe.getValue()[5]))
				this.arecipes.add(new SmeltingSet(
						(ItemStack)recipe.getKey()[0],
						(ItemStack)recipe.getKey()[1],
						(ItemStack)recipe.getKey()[2],
						(ItemStack)recipe.getKey()[3],
						(ItemStack)recipe.getKey()[4],
						(ItemStack)recipe.getKey()[5],
						(ItemStack)recipe.getValue()[0],
						(ItemStack)recipe.getValue()[1],
						(ItemStack)recipe.getValue()[2],
						(ItemStack)recipe.getValue()[3],
						(ItemStack)recipe.getValue()[4],
						(ItemStack)recipe.getValue()[5],
						(ItemStack)recipe.getKey()[6]));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("chemistry")) && getClass() == ChemplantRecipeHandler.class) {
			loadCraftingRecipes("chemistry", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object[], Object[]> recipes = MachineRecipes.instance().getChemistryRecipes();
		for (Map.Entry<Object[], Object[]> recipe : recipes.entrySet()) {
			if (compareFluidStacks(ingredient, (ItemStack)recipe.getKey()[0]) || 
					compareFluidStacks(ingredient, (ItemStack)recipe.getKey()[1]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[2]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[3]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[4]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[5]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[6]))
				this.arecipes.add(new SmeltingSet(
						(ItemStack)recipe.getKey()[0],
						(ItemStack)recipe.getKey()[1],
						(ItemStack)recipe.getKey()[2],
						(ItemStack)recipe.getKey()[3],
						(ItemStack)recipe.getKey()[4],
						(ItemStack)recipe.getKey()[5],
						(ItemStack)recipe.getValue()[0],
						(ItemStack)recipe.getValue()[1],
						(ItemStack)recipe.getValue()[2],
						(ItemStack)recipe.getValue()[3],
						(ItemStack)recipe.getValue()[4],
						(ItemStack)recipe.getValue()[5],
						(ItemStack)recipe.getKey()[6]));			
		}
	}
	
	private boolean compareFluidStacks(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        //return GUITestDiFurnace.class;
    	return null;
    }
    
    @Override
    public void loadTransferRects() {
        transferRectsGui = new LinkedList<RecipeTransferRect>();
        guiGui = new LinkedList<Class<? extends GuiContainer>>();

        transferRects.add(new RecipeTransferRect(new Rectangle(138 - 1 - 72, 23, 18 * 3, 18), "chemistry"));
        transferRectsGui.add(new RecipeTransferRect(new Rectangle(18 * 2 + 2, 89 - 7 - 11, 18 * 5 - 4, 18 + 16), "chemistry"));
        guiGui.add(GUIMachineChemplant.class);
        RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
        RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
    }

    @Override
    public void drawExtras(int recipe) {

        drawProgressBar(83 - (18 * 4) - 9 + 1, 6, 0, 86, 16, 18 * 3 - 2, 480, 7);
        
        drawProgressBar(83 - 3 + 16 + 5 - 36, 5 + 18, 16, 86, 18 * 3, 18, 48, 0);
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return super.newInstance();
    }
}
