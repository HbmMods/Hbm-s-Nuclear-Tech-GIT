package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.gui.GUIMachineAssembler;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AssemblerRecipeHandler extends TemplateRecipeHandler {
	
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
		PositionedStack input5;
		PositionedStack input6;
		PositionedStack input7;
		PositionedStack input8;
		PositionedStack input9;
		PositionedStack input10;
		PositionedStack input11;
		PositionedStack input12;
		PositionedStack template;
        PositionedStack result;
    	
        public SmeltingSet(ItemStack input1, ItemStack input2, ItemStack input3,
        		ItemStack input4, ItemStack input5, ItemStack input6, ItemStack input7,
        		ItemStack input8, ItemStack input9, ItemStack input10, ItemStack input11,
        		ItemStack input12, ItemStack template, ItemStack result) {
            this.input1 = new PositionedStack(input1, 66 - 36, 6);
            this.input2 = new PositionedStack(input2, 66 - 18, 6);
            this.input3 = new PositionedStack(input3, 66, 6);
            this.input4 = new PositionedStack(input4, 66 + 18, 6);
            this.input5 = new PositionedStack(input5, 66 - 36, 6 + 18);
            this.input6 = new PositionedStack(input6, 66 - 18, 6 + 18);
            this.input7 = new PositionedStack(input7, 66, 6 + 18);
            this.input8 = new PositionedStack(input8, 66 + 18, 6 + 18);
            this.input9 = new PositionedStack(input9, 66 - 36, 6 + 36);
            this.input10 = new PositionedStack(input10, 66 - 18, 6 + 36);
            this.input11 = new PositionedStack(input11, 66, 6 + 36);
            this.input12 = new PositionedStack(input12, 66 + 18, 6 + 36);
            this.template = new PositionedStack(template, 66 + 45, 6);
            this.result = new PositionedStack(result, 138, 24);
        }

        @Override
		public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] {input1, input2, input3, input4, input5, input6, input7, input8, input9, input10, input11, input12, template}));
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
		return RefStrings.MODID + ":textures/gui/gui_nei_assembler.png";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("assembly")) && getClass() == AssemblerRecipeHandler.class) {
			Map<Object[], Object> recipes = MachineRecipes.instance().getAssemblyRecipes();
			for (Map.Entry<Object[], Object> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet(
						(ItemStack)recipe.getKey()[0],
						(ItemStack)recipe.getKey()[1],
						(ItemStack)recipe.getKey()[2],
						(ItemStack)recipe.getKey()[3],
						(ItemStack)recipe.getKey()[4],
						(ItemStack)recipe.getKey()[5],
						(ItemStack)recipe.getKey()[6],
						(ItemStack)recipe.getKey()[7],
						(ItemStack)recipe.getKey()[8],
						(ItemStack)recipe.getKey()[9],
						(ItemStack)recipe.getKey()[10],
						(ItemStack)recipe.getKey()[11],
						(ItemStack)recipe.getKey()[12],
						(ItemStack)recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object[], Object> recipes = MachineRecipes.instance().getAssemblyRecipes();
		for (Map.Entry<Object[], Object> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameTypeCrafting((ItemStack)recipe.getValue(), result))
				this.arecipes.add(new SmeltingSet(
						(ItemStack)recipe.getKey()[0],
						(ItemStack)recipe.getKey()[1],
						(ItemStack)recipe.getKey()[2],
						(ItemStack)recipe.getKey()[3],
						(ItemStack)recipe.getKey()[4],
						(ItemStack)recipe.getKey()[5],
						(ItemStack)recipe.getKey()[6],
						(ItemStack)recipe.getKey()[7],
						(ItemStack)recipe.getKey()[8],
						(ItemStack)recipe.getKey()[9],
						(ItemStack)recipe.getKey()[10],
						(ItemStack)recipe.getKey()[11],
						(ItemStack)recipe.getKey()[12],
						(ItemStack)recipe.getValue()));
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
		Map<Object[], Object> recipes = MachineRecipes.instance().getAssemblyRecipes();
		for (Map.Entry<Object[], Object> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType(ingredient, (ItemStack)recipe.getKey()[0]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[1]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[2]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[3]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[4]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[5]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[6]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[7]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[8]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[9]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[10]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[11]) || 
					NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()[12]))
				this.arecipes.add(new SmeltingSet(
						(ItemStack)recipe.getKey()[0],
						(ItemStack)recipe.getKey()[1],
						(ItemStack)recipe.getKey()[2],
						(ItemStack)recipe.getKey()[3],
						(ItemStack)recipe.getKey()[4],
						(ItemStack)recipe.getKey()[5],
						(ItemStack)recipe.getKey()[6],
						(ItemStack)recipe.getKey()[7],
						(ItemStack)recipe.getKey()[8],
						(ItemStack)recipe.getKey()[9],
						(ItemStack)recipe.getKey()[10],
						(ItemStack)recipe.getKey()[11],
						(ItemStack)recipe.getKey()[12],
						(ItemStack)recipe.getValue()));				
		}
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

        transferRects.add(new RecipeTransferRect(new Rectangle(138 - 1 - 36, 23, 36, 18), "assembly"));
        transferRectsGui.add(new RecipeTransferRect(new Rectangle(18 * 2 + 2, 89 - 7 - 11, 18 * 5 - 4, 18 + 16), "assembly"));
        guiGui.add(GUIMachineAssembler.class);
        RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
        RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
    }

    @Override
    public void drawExtras(int recipe) {

        drawProgressBar(83 - (18 * 4) - 9 + 1, 6, 0, 86, 16, 18 * 3 - 2, 480, 7);
        
        drawProgressBar(83 - 3 + 16 + 5, 5 + 18, 16, 86, 36, 18, 48, 0);
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return super.newInstance();
    }

}
