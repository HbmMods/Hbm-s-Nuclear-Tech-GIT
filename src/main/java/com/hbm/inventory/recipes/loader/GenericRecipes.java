package com.hbm.inventory.recipes.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

/**
 * Fully genericized recipes.
 * Features:
 * * Fluid in and output
 * * AStack intput
 * * Chance-based outputs, for selecting items and for selecting items are produced in the first place
 * * Duration
 * * Tags for identification
 * 
 * @author hbm
 */
public abstract class GenericRecipes extends SerializableRecipe {
	
	public static final Random RNG = new Random();

	public List<GenericRecipe> recipeOrderedList = new ArrayList();
	public HashMap<String, GenericRecipe> recipeNameMap = new HashMap();

	public abstract int inputItemLimit();
	public abstract int inputFluidLimit();
	public abstract int outputItemLimit();
	public abstract int outputFluidLimit();

	@Override
	public Object getRecipeObject() {
		return this.recipeOrderedList;
	}

	@Override
	public void deleteRecipes() {
		this.recipeOrderedList.clear();
		this.recipeNameMap.clear();
	}
	
	public void register(GenericRecipe recipe) {
		this.recipeOrderedList.add(recipe);
		this.recipeNameMap.put(recipe.name, recipe);
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		
	}

	///////////////
	/// CLASSES ///
	///////////////
	
	public static class GenericRecipe {
		
		public String name;
		public AStack[] inputItem;
		public FluidStack[] inputFluid;
		public IOutput[] outputItem;
		public FluidStack[] outputFluid;
		public int duration;
		
		public GenericRecipe(String name, int duration) {
			this.name = name;
			this.duration = duration;
		}
	}
	
	public static interface IOutput {
		public boolean possibleMultiOutput();
		public ItemStack collapse();
	}
	
	/** A chance output, produces either an ItemStack or null */
	public static class ChanceOutput extends WeightedRandom.Item implements IOutput {
		
		public ItemStack stack;
		public float chance;

		public ChanceOutput(ItemStack stack) { this(stack, 1F, 1); }
		public ChanceOutput(ItemStack stack, int weight) { this(stack, 1F, weight); }
		public ChanceOutput(ItemStack stack, float chance, int weight) {
			super(weight);
			this.stack = stack;
			this.chance = chance;
		}

		@Override 
		public ItemStack collapse() {
			if(this.chance >= 1F) return this.stack;
			return RNG.nextFloat() <= chance ? this.stack : null;
		}
		
		@Override public boolean possibleMultiOutput() { return false; }
	}
	
	/** Multiple choice chance output, produces a ChanceOutput chosen randomly by weight */
	public static class ChanceOutputMulti implements IOutput {
		
		public List<ChanceOutput> pool = new ArrayList();
		
		@Override public ItemStack collapse() { return ((ChanceOutput) WeightedRandom.getRandomItem(RNG, pool)).collapse(); }
		@Override public boolean possibleMultiOutput() { return pool.size() > 1; }
	}
}
