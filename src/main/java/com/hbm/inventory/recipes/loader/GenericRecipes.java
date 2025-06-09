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

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
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
public abstract class GenericRecipes<T extends GenericRecipe> extends SerializableRecipe {
	
	public static final Random RNG = new Random();

	public List<T> recipeOrderedList = new ArrayList();
	public HashMap<String, T> recipeNameMap = new HashMap();

	public abstract int inputItemLimit();
	public abstract int inputFluidLimit();
	public abstract int outputItemLimit();
	public abstract int outputFluidLimit();
	public boolean hasDuration() { return true; }
	public boolean hasPower() { return true; }

	@Override
	public Object getRecipeObject() {
		return this.recipeOrderedList;
	}

	@Override
	public void deleteRecipes() {
		this.recipeOrderedList.clear();
		this.recipeNameMap.clear();
	}
	
	public void register(T recipe) {
		this.recipeOrderedList.add(recipe);
		if(recipeNameMap.containsKey(recipe.name)) throw new IllegalStateException("Recipe " + recipe.name + " has been reciped with a duplicate ID!");
		this.recipeNameMap.put(recipe.name, recipe);
	}

	@Override
	public void readRecipe(JsonElement element) {
		JsonObject obj = (JsonObject) element;
		T recipe = instantiateRecipe(obj.get("name").getAsString());

		if(this.inputItemLimit() > 0 && obj.has("inputItem")) recipe.inputItem = this.readAStackArray(obj.get("inputItem").getAsJsonArray());
		if(this.inputFluidLimit() > 0 && obj.has("inputFluid")) recipe.inputFluid = this.readFluidArray(obj.get("inputFluid").getAsJsonArray());
		
		if(this.outputItemLimit() > 0 && obj.has("outputItem")) recipe.outputItem = this.readOutputArray(obj.get("outputItem").getAsJsonArray());
		if(this.outputFluidLimit() > 0 && obj.has("outputFluid")) recipe.outputFluid = this.readFluidArray(obj.get("outputFluid").getAsJsonArray());

		if(this.hasDuration()) recipe.setDuration(obj.get("duration").getAsInt());
		if(this.hasPower()) recipe.setPower(obj.get("power").getAsLong());
		
		if(obj.has("icon")) recipe.setIcon(this.readItemStack(obj.get("icon").getAsJsonArray()));
		if(obj.has("named") && obj.get("named").getAsBoolean()) recipe.setNamed();
		
		readExtraData(element, recipe);
		
		register(recipe);
	}

	public abstract T instantiateRecipe(String name);
	public void readExtraData(JsonElement element, T recipe) { }

	@Override
	public void writeRecipe(Object recipeObject, JsonWriter writer) throws IOException {
		T recipe = (T) recipeObject;
		
		writer.name("name").value(recipe.name);
		
		if(this.inputItemLimit() > 0 && recipe.inputItem != null) {
			writer.name("inputItem").beginArray();
			for(AStack stack : recipe.inputItem) this.writeAStack(stack, writer);
			writer.endArray();
		}
		
		if(this.inputFluidLimit() > 0 && recipe.inputFluid != null) {
			writer.name("inputFluid").beginArray();
			for(FluidStack stack : recipe.inputFluid) this.writeFluidStack(stack, writer);
			writer.endArray();
		}
		
		if(this.outputItemLimit() > 0 && recipe.outputItem != null) {
			writer.name("outputItem").beginArray();
			for(IOutput stack : recipe.outputItem) stack.serialize(writer);
			writer.endArray();
		}
		
		if(this.outputFluidLimit() > 0 && recipe.outputFluid != null) {
			writer.name("outputFluid").beginArray();
			for(FluidStack stack : recipe.outputFluid) this.writeFluidStack(stack, writer);
			writer.endArray();
		}

		if(this.hasDuration()) writer.name("duration").value(recipe.duration);
		if(this.hasPower()) writer.name("power").value(recipe.power);
		
		if(recipe.writeIcon) {
			writer.name("icon");
			this.writeItemStack(recipe.icon, writer);
		}
		
		if(recipe.customLocalization) writer.name("named").value(true);
		
		writeExtraData(recipe, writer);
	}
	
	public void writeExtraData(T recipe, JsonWriter writer) { }
	
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
	public static interface IOutput {
		/** true for ChanceOutputMulti with a poolsize >1 */
		public boolean possibleMultiOutput();
		/** Decides an output, returns a copy of the held result */
		public ItemStack collapse();
		/** Returns an itemstack only if possibleMultiOutput is false, null otherwise */
		public ItemStack getSingle();
		public ItemStack[] getAllPossibilities();
		public void serialize(JsonWriter writer) throws IOException;
		public void deserialize(JsonArray array);
		public String[] getLabel();
	}
	
	/** A chance output, produces either an ItemStack or null */
	public static class ChanceOutput extends WeightedRandom.Item implements IOutput {
		
		// a weight of 0 means this output is not part of a weighted output
		
		public ItemStack stack;
		public float chance;

		public ChanceOutput() { super(0); } // for deserialization
		public ChanceOutput(ItemStack stack) { this(stack, 1F, 0); }
		public ChanceOutput(ItemStack stack, int weight) { this(stack, 1F, weight); }
		public ChanceOutput(ItemStack stack, float chance) { this(stack, chance, 0); }
		public ChanceOutput(ItemStack stack, float chance, int weight) {
			super(weight);
			this.stack = stack;
			this.chance = chance;
		}

		@Override 
		public ItemStack collapse() {
			if(this.chance >= 1F) return getSingle().copy();
			return RNG.nextFloat() <= chance ? getSingle().copy() : null;
		}
		
		@Override public ItemStack getSingle() { return this.stack; }
		@Override public boolean possibleMultiOutput() { return false; }
		@Override public ItemStack[] getAllPossibilities() { return new ItemStack[] {getSingle()}; }
		
		@Override
		public void serialize(JsonWriter writer) throws IOException {
			boolean standardStack = chance >= 1 && itemWeight == 0;
			
			writer.beginArray();
			writer.setIndent("");
			
			if(itemWeight == 0) writer.value("single");
			SerializableRecipe.writeItemStack(stack, writer);
			writer.setIndent("");
			
			if(!standardStack) {
				writer.value(chance);
				if(itemWeight > 0) writer.value(itemWeight);
			}
			
			writer.endArray();
			writer.setIndent("  ");
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
		
		@Override
		public String[] getLabel() {
			return new String[] {EnumChatFormatting.GRAY + "" + this.stack.stackSize + "x " + this.stack.getDisplayName() + (this.chance >= 1 ? "" : " (" + (int)(this.chance * 1000) / 10F + "%)")};
		}
	}
	
	/** Multiple choice chance output, produces a ChanceOutput chosen randomly by weight */
	public static class ChanceOutputMulti implements IOutput {
		
		public List<ChanceOutput> pool = new ArrayList();
		
		@Override public ItemStack collapse() { return ((ChanceOutput) WeightedRandom.getRandomItem(RNG, pool)).collapse(); }
		@Override public boolean possibleMultiOutput() { return pool.size() > 1; }
		@Override public ItemStack getSingle() { return possibleMultiOutput() ? null : pool.get(0).getSingle(); }
		
		@Override public ItemStack[] getAllPossibilities() {
			ItemStack[] outputs = new ItemStack[pool.size()];
			for(int i = 0; i < outputs.length; i++) outputs[i] = pool.get(i).getAllPossibilities()[0];
			return outputs;
		}
		
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
		
		@Override
		public String[] getLabel() {
			String[] label = new String[pool.size() + 1];
			label[0] = "One of:";
			int totalWeight = WeightedRandom.getTotalWeight(pool);
			for(int i = 1; i < label.length; i++) {
				ChanceOutput output = pool.get(i - 1);
				float chance = (float) output.itemWeight / (float) totalWeight * output.chance;
				label[i] = "  " + EnumChatFormatting.GRAY + output.stack.stackSize + "x " + output.stack.getDisplayName() + " (" + (int)(chance * 1000F) / 10F + "%)";
			}
			return label;
		}
	}
}
