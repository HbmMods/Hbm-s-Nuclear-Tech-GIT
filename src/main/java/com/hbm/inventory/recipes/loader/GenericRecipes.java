package com.hbm.inventory.recipes.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

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
	public void readRecipe(JsonElement element) {
		JsonObject obj = (JsonObject) element;
		GenericRecipe recipe = instantiateRecipe(obj.get("name").getAsString(), obj.get("duration").getAsInt());

		if(this.inputItemLimit() > 0) recipe.inputItem = this.readAStackArray(obj.get("inputItem").getAsJsonArray());
		if(this.inputFluidLimit() > 0) recipe.inputFluid = this.readFluidArray(obj.get("inputFluid").getAsJsonArray());
		
		if(this.outputItemLimit() > 0) recipe.outputItem = this.readOutputArray(obj.get("outputItem").getAsJsonArray());
		if(this.outputFluidLimit() > 0) recipe.outputFluid = this.readFluidArray(obj.get("outputFluid").getAsJsonArray());
		
		readExtraData(element, recipe);
		
		register(recipe);
	}

	public GenericRecipe instantiateRecipe(String name, int duration) { return new GenericRecipe(name, duration); }
	public void readExtraData(JsonElement element, GenericRecipe recipe) { }

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		
	}
	
	public void writeExtraData(Object recipe, JsonWriter writer) { }
	
	public IOutput[] readOutputArray(JsonArray array) {
		IOutput[] output = new IOutput[array.size()];
		int index = 0;
		
		for(JsonElement element : array) {
			JsonArray arrayElement = element.getAsJsonArray();
			String type = arrayElement.get(0).getAsString();
			if("single".equals(type)) {
				ChanceOutput co = new ChanceOutput();
				co.deserialize(arrayElement);
				output[index] = co;
			} else if("multi".equals(type)) {
				ChanceOutputMulti com = new ChanceOutputMulti();
				com.deserialize(arrayElement);
				output[index] = com;
			} else {
				throw new IllegalArgumentException("Invalid IOutput type '" + type + "', expected 'single' or 'multi' for recipe " + array.toString());
			}
			index++;
		}
		
		return output;
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
		public ItemStack icon;
		
		public GenericRecipe(String name, int duration) {
			this.name = name;
			this.duration = duration;
		}
		
		public GenericRecipe setIcon(ItemStack icon) {
			this.icon = icon;
			return this;
		}
		
		public ItemStack getIcon() {
			
			if(icon == null) {
				if(outputItem != null) {
					if(outputItem[0] instanceof ChanceOutput) icon = ((ChanceOutput) outputItem[0]).stack.copy();
					if(outputItem[0] instanceof ChanceOutputMulti) icon = ((ChanceOutputMulti) outputItem[0]).pool.get(0).stack.copy();
					return icon;
				}
				if(outputFluid != null) {
					icon = ItemFluidIcon.make(outputFluid[0]);
				}
			}
			
			if(icon == null) icon = new ItemStack(ModItems.nothing);
			return icon;
		}
	}
	
	public static interface IOutput {
		public boolean possibleMultiOutput();
		public ItemStack collapse();
		public void serialize(JsonWriter writer) throws IOException;
		public void deserialize(JsonArray array);
	}
	
	/** A chance output, produces either an ItemStack or null */
	public static class ChanceOutput extends WeightedRandom.Item implements IOutput {
		
		// a weight of 0 means this output is not part of a weighted output
		
		public ItemStack stack;
		public float chance;

		public ChanceOutput() { super(0); } // for deserialization
		public ChanceOutput(ItemStack stack) { this(stack, 1F, 0); }
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
		
		@Override
		public void serialize(JsonWriter writer) throws IOException {
			boolean standardStack = chance >= 1 && itemWeight == 0;
			if(!standardStack) writer.beginArray();
			
			if(itemWeight == 0) writer.value("single");
			SerializableRecipe.writeItemStack(stack, writer);
			
			if(!standardStack) {
				writer.value(chance);
				if(itemWeight > 0) writer.value(itemWeight);
				writer.endArray();
			}
		}
		
		@Override
		public void deserialize(JsonArray array) {
			
			if(array.get(0).isJsonPrimitive()) { // "single" tag, don't apply weight
				this.stack = SerializableRecipe.readItemStack(array.get(1).getAsJsonArray());
				if(array.size() > 2) this.chance = array.get(2).getAsFloat();
			} else { // hopefully an array, therefore a weighted result
				this.stack = SerializableRecipe.readItemStack(array.get(0).getAsJsonArray());
				if(array.size() > 1) this.chance = array.get(1).getAsFloat();
				if(array.size() > 2) this.itemWeight = array.get(2).getAsInt();
			}
		}
	}
	
	/** Multiple choice chance output, produces a ChanceOutput chosen randomly by weight */
	public static class ChanceOutputMulti implements IOutput {
		
		public List<ChanceOutput> pool = new ArrayList();
		
		@Override public ItemStack collapse() { return ((ChanceOutput) WeightedRandom.getRandomItem(RNG, pool)).collapse(); }
		@Override public boolean possibleMultiOutput() { return pool.size() > 1; }
		
		@Override
		public void serialize(JsonWriter writer) throws IOException {
			writer.beginArray();
			writer.value("multi");
			for(ChanceOutput output : pool) output.serialize(writer);
			writer.endArray();
		}
		
		@Override
		public void deserialize(JsonArray array) {
			for(JsonElement element : array) {
				ChanceOutput output = new ChanceOutput();
				output.deserialize(element.getAsJsonArray());
				pool.add(output);
			}
		}
	}
}
