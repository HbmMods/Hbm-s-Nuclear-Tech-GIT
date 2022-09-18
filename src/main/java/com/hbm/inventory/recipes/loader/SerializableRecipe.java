package com.hbm.inventory.recipes.loader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.*;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

//the anti-spaghetti. this class provides so much functionality and saves so much time, i just love you, SerializableRecipe <3
public abstract class SerializableRecipe {
	
	public static final Gson gson = new Gson();
	public static List<SerializableRecipe> recipeHandlers = new ArrayList();
	
	/*
	 * INIT
	 */
	
	public static void registerAllHandlers() {
		recipeHandlers.add(new ShredderRecipes());
		recipeHandlers.add(new ChemplantRecipes());
		recipeHandlers.add(new CrucibleRecipes());
		recipeHandlers.add(new CentrifugeRecipes());
		recipeHandlers.add(new CyclotronRecipes());
		recipeHandlers.add(new HadronRecipes());
		recipeHandlers.add(new FuelPoolRecipes());
	}
	
	public static void initialize() {
		File recDir = new File(MainRegistry.configDir.getAbsolutePath() + File.separatorChar + "hbmRecipes");
		
		if(!recDir.exists()) {
			if(!recDir.mkdir()) {
				throw new IllegalStateException("Unable to make recipe directory " + recDir.getAbsolutePath());
			}
		}
		
		MainRegistry.logger.info("Starting recipe init!");
		
		for(SerializableRecipe recipe : recipeHandlers) {
			
			recipe.deleteRecipes();
			
			File recFile = new File(recDir.getAbsolutePath() + File.separatorChar + recipe.getFileName());
			if(recFile.exists() && recFile.isFile()) {
				MainRegistry.logger.info("Reading recipe file " + recFile.getName());
				recipe.readRecipeFile(recFile);
			} else {
				MainRegistry.logger.info("No recipe file found, registering defaults for " + recipe.getFileName());
				recipe.registerDefaults();
				
				File recTemplate = new File(recDir.getAbsolutePath() + File.separatorChar + "_" + recipe.getFileName());
				MainRegistry.logger.info("Writing template file " + recTemplate.getName());
				recipe.writeTemplateFile(recTemplate);
			}
			
			recipe.registerPost();
		}
		
		MainRegistry.logger.info("Finished recipe init!");
	}

	/*
	 * ABSTRACT
	 */
	
	/** The machine's (or process') name used for the recipe file */
	public abstract String getFileName();
	/** Return the list object holding all the recipes, usually an ArrayList or HashMap */
	public abstract Object getRecipeObject();
	/** Will use the supplied JsonElement (usually casts to JsonArray) from the over arching recipe array and adds the recipe to the recipe list object */
	public abstract void readRecipe(JsonElement recipe);
	/** Is given a single recipe from the recipe list object (a wrapper, Tuple, array, HashMap Entry, etc) and writes it to the current ongoing GSON stream 
	 * @throws IOException */
	public abstract void writeRecipe(Object recipe, JsonWriter writer) throws IOException;
	/** Registers the default recipes */
	public abstract void registerDefaults();
	/** Deletes all existing recipes, currenly unused */
	public abstract void deleteRecipes();
	/** A routine called after registering all recipes, whether it's a template or not. Good for IMC functionality. */
	public void registerPost() { }
	/** Returns a string to be printed as info at the top of the JSON file */
	public String getComment() {
		return null;
	}
	
	/*
	 * JSON R/W WRAPPERS
	 */
	
	public void writeTemplateFile(File template) {
		
		try {
			/* Get the recipe list object */
			Object recipeObject = this.getRecipeObject();
			List recipeList = new ArrayList();
			
			/* Try to pry all recipes from our list */
			if(recipeObject instanceof Collection) {
				recipeList.addAll((Collection) recipeObject);
				
			} else if(recipeObject instanceof HashMap) {
				recipeList.addAll(((HashMap) recipeObject).entrySet());
			}
			
			if(recipeList.isEmpty())
				throw new IllegalStateException("Error while writing recipes for " + this.getClass().getSimpleName() + ": Recipe list is either empty or in an unsupported format!");
			
			JsonWriter writer = new JsonWriter(new FileWriter(template));
			writer.setIndent("  ");					//pretty formatting
			writer.beginObject();					//initial '{'
			
			if(this.getComment() != null) {
				writer.name("comment").value(this.getComment());
			}
			
			writer.name("recipes").beginArray();	//all recipes are stored in an array called "recipes"
			
			for(Object recipe : recipeList) {
				writer.beginObject();				//begin object for a single recipe
				this.writeRecipe(recipe, writer);	//serialize here
				writer.endObject();					//end recipe object
			}
			
			writer.endArray();						//end recipe array
			writer.endObject();						//final '}'
			writer.close();
		} catch(Exception ex) { }
	}
	
	public void readRecipeFile(File file) {
		
		try {
			JsonObject json = gson.fromJson(new FileReader(file), JsonObject.class);
			JsonArray recipes = json.get("recipes").getAsJsonArray();
			for(JsonElement recipe : recipes) {
				this.readRecipe(recipe);
			}
		} catch(Exception ex) { }
	}
	
	/*
	 * JSON IO UTIL
	 */
	
	protected static AStack readAStack(JsonArray array) {
		try {
			String type = array.get(0).getAsString();
			int stacksize = array.size() > 2 ? array.get(2).getAsInt() : 1;
			if("item".equals(type)) {
				Item item = (Item) Item.itemRegistry.getObject(array.get(1).getAsString());
				int meta = array.size() > 3 ? array.get(3).getAsInt() : 0;
				return new ComparableStack(item, stacksize, meta);
			}
			if("dict".equals(type)) {
				String dict = array.get(1).getAsString();
				return new OreDictStack(dict, stacksize);
			}
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading stack array " + array.toString());
		return new ComparableStack(ModItems.nothing);
	}
	
	protected static AStack[] readAStackArray(JsonArray array) {
		try {
			AStack[] items = new AStack[array.size()];
			for(int i = 0; i < items.length; i++) { items[i] = readAStack((JsonArray) array.get(i)); }
			return items;
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading stack array " + array.toString());
		return new AStack[0];
	}
	
	protected static void writeAStack(AStack astack, JsonWriter writer) throws IOException {
		writer.beginArray();
		writer.setIndent("");
		if(astack instanceof ComparableStack) {
			ComparableStack comp = (ComparableStack) astack;
			writer.value("item");														//ITEM  identifier
			writer.value(Item.itemRegistry.getNameForObject(comp.toStack().getItem()));	//item name
			if(comp.stacksize != 1) writer.value(comp.stacksize);						//stack size
			if(comp.meta > 0) writer.value(comp.meta);									//metadata
		}
		if(astack instanceof OreDictStack) {
			OreDictStack ore = (OreDictStack) astack;
			writer.value("dict");			//DICT identifier
			writer.value(ore.name);			//dict name
			writer.value(ore.stacksize);	//stacksize
		}
		writer.endArray();
		writer.setIndent("  ");
	}
	
	protected static ItemStack readItemStack(JsonArray array) {
		try {
			Item item = (Item) Item.itemRegistry.getObject(array.get(0).getAsString());
			int stacksize = array.size() > 1 ? array.get(1).getAsInt() : 1;
			int meta = array.size() > 2 ? array.get(2).getAsInt() : 0;
			return new ItemStack(item, stacksize, meta);
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading stack array " + array.toString());
		return new ItemStack(ModItems.nothing);
	}
	
	protected static ItemStack[] readItemStackArray(JsonArray array) {
		try {
			ItemStack[] items = new ItemStack[array.size()];
			for(int i = 0; i < items.length; i++) { items[i] = readItemStack((JsonArray) array.get(i)); }
			return items;
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading stack array " + array.toString());
		return new ItemStack[0];
	}
	
	protected static void writeItemStack(ItemStack stack, JsonWriter writer) throws IOException {
		writer.beginArray();
		writer.setIndent("");
		writer.value(Item.itemRegistry.getNameForObject(stack.getItem()));						//item name
		if(stack.stackSize != 1 || stack.getItemDamage() != 0) writer.value(stack.stackSize);	//stack size
		if(stack.getItemDamage() != 0) writer.value(stack.getItemDamage());						//metadata
		writer.endArray();
		writer.setIndent("  ");
	}
	
	protected static FluidStack readFluidStack(JsonArray array) {
		try {
			FluidType type = Fluids.fromName(array.get(0).getAsString());
			int fill = array.get(1).getAsInt();
			return new FluidStack(type, fill);
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading fluid array " + array.toString());
		return new FluidStack(Fluids.NONE, 0);
	}
	
	protected static FluidStack[] readFluidArray(JsonArray array) {
		try {
			FluidStack[] fluids = new FluidStack[array.size()];
			for(int i = 0; i < fluids.length; i++) { fluids[i] = readFluidStack((JsonArray) array.get(i)); }
			return fluids;
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading fluid array " + array.toString());
		return new FluidStack[0];
	}
	
	protected static void writeFluidStack(FluidStack stack, JsonWriter writer) throws IOException {
		writer.beginArray();
		writer.setIndent("");
		writer.value(stack.type.getName());	//fluid type
		writer.value(stack.fill);			//amount in mB
		writer.endArray();
		writer.setIndent("  ");
	}
}
