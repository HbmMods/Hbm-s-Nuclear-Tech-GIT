package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.handler.nei.CyclotronRecipeHandler.SmeltingSet;
import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.gui.GUIMachineCyclotron;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRectHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ModInfoHandler extends TemplateRecipeHandler {

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe
    {
        PositionedStack item;
    	
        public SmeltingSet(ItemStack item) {
        	item.stackSize = 1;
            this.item = new PositionedStack(item, 129 - 54, 24 - 17);
        }

        @Override
		public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] {item}));
        }

        @Override
		public PositionedStack getResult() {
            return item;
        }
    }
    
	@Override
	public String getRecipeName() {
		return "Mod Info";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/gui_info.png";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("hbminfo")) && getClass() == ModInfoHandler.class) {
			Map<Object, Object[]> recipes = MachineRecipes.instance().getItemInfo();
			for (Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object, Object[]> recipes = MachineRecipes.instance().getItemInfo();
		for (Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType((ItemStack)recipe.getKey(), result))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("hbminfo")) && getClass() == ModInfoHandler.class) {
			loadCraftingRecipes("hbminfo", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object, Object[]> recipes = MachineRecipes.instance().getItemInfo();
		for (Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType(ingredient, (ItemStack)recipe.getKey()))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey()));				
		}
	}

	FontRenderer font = Minecraft.getMinecraft().fontRenderer;
	
    @Override
    public void drawExtras(int recipe) {
    	ItemStack stack = arecipes.get(recipe).getResult().item;
    	String[] strings = MachineRecipes.instance().getInfoFromItem(stack);
    	
    	font.drawString(stack.getDisplayName() + ":", 5, 24 + 5, 4210752);
    	
    	for(int i = 0; i < strings.length; i++)
        	font.drawString(strings[i], 5 + 3, 34 + 5 + 10 * i, 4210752);
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return super.newInstance();
    }
}
