package com.hbm.handler.nei;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.lib.RefStrings;
import com.hbm.util.InventoryUtil;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class NEIUniversalHandler extends TemplateRecipeHandler {
	
	/// SETUP ///
	public final String display;
	public final String key;
	public final ItemStack[] machine;
	public final HashMap<Object, Object> recipes;
	/// SETUP ///
	
	public NEIUniversalHandler(String key, String display, ItemStack machine[], HashMap recipes) {
		this.key = key;
		this.display = display;
		this.machine = machine;
		this.recipes = recipes;
	}

	public NEIUniversalHandler(String key, String display, ItemStack machine, HashMap recipes) {		this(key, display, new ItemStack[]{machine}, recipes); }
	public NEIUniversalHandler(String key, String display, Item machine, HashMap recipes) {		this(key, display, new ItemStack(machine), recipes); }
	public NEIUniversalHandler(String key, String display, Block machine, HashMap recipes) {	this(key, display, new ItemStack(machine), recipes); }

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
		
		PositionedStack[] input;
		PositionedStack[] output;
		PositionedStack machinePositioned;

		public RecipeSet(ItemStack[][] in, ItemStack[][] out) {
			
			input = new PositionedStack[in.length];
			for(int i = 0; i < in.length; i++) {
				ItemStack[] sub = in[i];
				this.input[i] = new PositionedStack(sub, 48 + i * 18, 24);
			}
			output = new PositionedStack[out.length];
			for(int i = 0; i < out.length; i++) {
				ItemStack[] sub = out[i];
				this.output[i] = new PositionedStack(sub, 102 + i * 18, 24);
			}
			
			this.machinePositioned = new PositionedStack(machine, 75, 31);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, Arrays.asList(input));
		}

		@Override
		public PositionedStack getResult() {
			return output[0];
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> other = new ArrayList();
			for(PositionedStack pos : output) {
				other.add(pos);
			}
			other.add(machinePositioned);
			return getCycledIngredients(cycleticks / 20, other);
		}
	}

	@Override
	public String getRecipeName() {
		return this.display;
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei.png";
	}

	@Override
	public void drawBackground(int recipe) {
		super.drawBackground(recipe);
		drawTexturedModalRect(47, 23, 5, 87, 18, 18);
		drawTexturedModalRect(101, 23, 5, 87, 18, 18);
		drawTexturedModalRect(74, 14, 59, 87, 18, 38);
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals(key)) {
			
			for(Entry<Object, Object> recipe : recipes.entrySet()) {
				ItemStack[][] ins = InventoryUtil.extractObject(recipe.getKey());
				ItemStack[][] outs = InventoryUtil.extractObject(recipe.getValue());
				this.arecipes.add(new RecipeSet(ins, outs));
			}
			
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		for(Entry<Object, Object> recipe : recipes.entrySet()) {
			ItemStack[][] ins = InventoryUtil.extractObject(recipe.getKey());
			ItemStack[][] outs = InventoryUtil.extractObject(recipe.getValue());
			
			match:
			for(ItemStack[] array : outs) {
				for(ItemStack stack : array) {
					if(NEIServerUtils.areStacksSameTypeCrafting(stack, result)) {
						this.arecipes.add(new RecipeSet(ins, outs));
						break match;
					}
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals(key)) {
			loadCraftingRecipes(key, new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		for(Entry<Object, Object> recipe : recipes.entrySet()) {
			ItemStack[][] ins = InventoryUtil.extractObject(recipe.getKey());
			ItemStack[][] outs = InventoryUtil.extractObject(recipe.getValue());
			
			match:
			for(ItemStack[] array : ins) {
				for(ItemStack stack : array) {
					if(NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) {
						this.arecipes.add(new RecipeSet(ins, outs));
						break match;
					}
				}
			}
		}
	}
}
