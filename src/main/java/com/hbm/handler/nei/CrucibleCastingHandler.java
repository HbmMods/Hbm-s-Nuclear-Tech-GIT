package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.imc.ICompatNHNEI;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.items.machine.ItemMold;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class CrucibleCastingHandler extends TemplateRecipeHandler implements ICompatNHNEI {

	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[]{
				new ItemStack(ModBlocks.foundry_basin),
				new ItemStack(ModBlocks.foundry_mold),
				new ItemStack(ModBlocks.machine_strand_caster)};
	}
	@Override
	public String getRecipeID() {
		return "ntmCrucibleFoundry";
	}
	
	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	
	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {

		PositionedStack input;
		PositionedStack mold;
		PositionedStack basin;
		PositionedStack output;
		
		public RecipeSet(ItemStack[] stacks) {
			this.input = new PositionedStack(stacks[0].copy(), 48, 24);
			this.mold = new PositionedStack(stacks[1].copy(), 75, 6);
			this.basin = new PositionedStack(stacks[2].copy(), 75, 42);
			//through reasons i cannot explain, stacks[3]'s stack size does not survive until this point.
			ItemStack o = ItemMold.moldById.get(stacks[1].getItemDamage()).getOutput(Mats.matById.get(stacks[0].getItemDamage()));
			this.output = new PositionedStack(o.copy(), 102, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, Arrays.asList(input, mold, basin));
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> other = new ArrayList();
			other.add(input);
			other.add(mold);
			other.add(basin);
			other.add(output);
			return getCycledIngredients(cycleticks / 20, other);
		}
	}

	@Override
	public String getRecipeName() {
		return "Crucible Casting";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_foundry.png";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals("ntmCrucibleFoundry")) {
			
			for(ItemStack[] recipe : CrucibleRecipes.getMoldRecipes()) {
				this.arecipes.add(new RecipeSet(recipe));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		for(ItemStack[] recipe : CrucibleRecipes.getMoldRecipes()) {
			if(NEIServerUtils.areStacksSameTypeCrafting(recipe[3], result)) {
				this.arecipes.add(new RecipeSet(recipe));
			}
		}
	}
	
	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("ntmCrucibleFoundry")) {
			loadCraftingRecipes("ntmCrucibleFoundry", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		for(ItemStack[] recipe : CrucibleRecipes.getMoldRecipes()) {
			
			for(int i = 0; i < 3; i++) {
				if(NEIServerUtils.areStacksSameTypeCrafting(recipe[i], ingredient)) {
					this.arecipes.add(new RecipeSet(recipe));
					break;
				}
			}
		}
	}
	
	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(65, 23, 36, 18), "ntmCrucibleFoundry"));
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
	}
}
