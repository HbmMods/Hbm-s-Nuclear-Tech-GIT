package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.imc.ICompatNHNEI;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.inventory.recipes.CrucibleRecipes.CrucibleRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemScraps;
import com.hbm.lib.RefStrings;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class CrucibleAlloyingHandler extends TemplateRecipeHandler implements ICompatNHNEI {
	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[]{
				new ItemStack(ModBlocks.machine_crucible)};
	}
	@Override
	public String getRecipeID() {
		return "ntmCrucibleAlloying";
	}
	
	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	
	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {

		List<PositionedStack> inputs = new ArrayList();
		PositionedStack template;
		PositionedStack crucible;
		List<PositionedStack> outputs = new ArrayList();
		
		public RecipeSet(CrucibleRecipe recipe) {
			List<ItemStack> inputs = new ArrayList();
			List<ItemStack> outputs = new ArrayList();
			for(MaterialStack stack : recipe.input) inputs.add(ItemScraps.create(stack, true));
			for(MaterialStack stack : recipe.output) outputs.add(ItemScraps.create(stack, true));
			
			this.template = new PositionedStack(new ItemStack(ModItems.crucible_template, 1, recipe.getId()), 75, 6);
			this.crucible = new PositionedStack(new ItemStack(ModBlocks.machine_crucible), 75, 42);
			
			for(int i = 0; i < inputs.size(); i++) {
				PositionedStack pos = new PositionedStack(inputs.get(i), 12 + (i % 3) * 18, 6 + (i / 3) * 18);
				this.inputs.add(pos);
			}
			
			for(int i = 0; i < outputs.size(); i++) {
				PositionedStack pos = new PositionedStack(outputs.get(i), 102 + (i % 3) * 18, 6 + (i / 3) * 18);
				this.outputs.add(pos);
			}
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, inputs);
		}

		@Override
		public PositionedStack getResult() {
			return outputs.get(0);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> other = new ArrayList();
			other.addAll(inputs);
			other.add(crucible);
			other.add(template);
			other.addAll(outputs);
			return getCycledIngredients(cycleticks / 20, other);
		}
	}

	@Override
	public String getRecipeName() {
		return "Crucible Alloying";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_crucible.png";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals("ntmCrucibleAlloying")) {

			for(CrucibleRecipe recipe : CrucibleRecipes.recipes) {
				this.arecipes.add(new RecipeSet(recipe));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		if(result.getItem() != ModItems.scraps)
			return;
		
		NTMMaterial material = Mats.matById.get(result.getItemDamage());

		for(CrucibleRecipe recipe : CrucibleRecipes.recipes) {
			
			for(MaterialStack stack : recipe.output) {
				if(stack.material == material) {
					this.arecipes.add(new RecipeSet(recipe));
					break;
				}
			}
		}
	}
	
	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("ntmCrucibleAlloying")) {
			loadCraftingRecipes("ntmCrucibleAlloying", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		if(ingredient.getItem() != ModItems.scraps)
			return;
		
		NTMMaterial material = Mats.matById.get(ingredient.getItemDamage());

		for(CrucibleRecipe recipe : CrucibleRecipes.recipes) {
			
			for(MaterialStack stack : recipe.input) {
				if(stack.material == material) {
					this.arecipes.add(new RecipeSet(recipe));
					break;
				}
			}
		}
	}
	
	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(65, 23, 36, 18), "ntmCrucibleAlloying"));
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
	}
}
