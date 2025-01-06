package com.hbm.inventory.recipes.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
import com.hbm.inventory.material.MatDistribution;
import com.hbm.inventory.recipes.*;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Pair;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

//the anti-spaghetti. this class provides so much functionality and saves so much time, i just love you, SerializableRecipe <3
public abstract class SerializableRecipe {
	
	public static final Gson gson = new Gson();
	public static List<SerializableRecipe> recipeHandlers = new ArrayList();
	
	public boolean modified = false;
	
	/*
	 * INIT
	 */
	
	public static void registerAllHandlers() {
		recipeHandlers.add(new PressRecipes());
		recipeHandlers.add(new BlastFurnaceRecipes());
		recipeHandlers.add(new ShredderRecipes());
		recipeHandlers.add(new SolderingRecipes());
		recipeHandlers.add(new ChemplantRecipes());
		recipeHandlers.add(new CombinationRecipes());
		recipeHandlers.add(new CrucibleRecipes());
		recipeHandlers.add(new CentrifugeRecipes());
		recipeHandlers.add(new CrystallizerRecipes());
		recipeHandlers.add(new FractionRecipes());
		recipeHandlers.add(new CrackingRecipes());
		recipeHandlers.add(new ReformingRecipes());
		recipeHandlers.add(new HydrotreatingRecipes());
		recipeHandlers.add(new LiquefactionRecipes());
		recipeHandlers.add(new SolidificationRecipes());
		recipeHandlers.add(new CokerRecipes());
		recipeHandlers.add(new PyroOvenRecipes());
		recipeHandlers.add(new BreederRecipes());
		recipeHandlers.add(new CyclotronRecipes());
		recipeHandlers.add(new HadronRecipes());
		recipeHandlers.add(new FuelPoolRecipes());
		recipeHandlers.add(new MixerRecipes());
		recipeHandlers.add(new OutgasserRecipes());
		recipeHandlers.add(new CompressorRecipes());
		recipeHandlers.add(new ElectrolyserFluidRecipes());
		recipeHandlers.add(new ElectrolyserMetalRecipes());
		recipeHandlers.add(new ArcWelderRecipes());
		recipeHandlers.add(new RotaryFurnaceRecipes());
		recipeHandlers.add(new ExposureChamberRecipes());
		recipeHandlers.add(new AmmoPressRecipes());
		recipeHandlers.add(new AssemblerRecipes());
		recipeHandlers.add(new PedestalRecipes());
		
		recipeHandlers.add(new MatDistribution());
		recipeHandlers.add(new CustomMachineRecipes());
		//AFTER MatDistribution
		recipeHandlers.add(new ArcFurnaceRecipes());
	}
	
	public static void initialize() {
		File recDir = new File(MainRegistry.configDir.getAbsolutePath() + File.separatorChar + "hbmRecipes");
		
		if(!recDir.exists()) {
			if(!recDir.mkdir()) {
				throw new IllegalStateException("Unable to make recipe directory " + recDir.getAbsolutePath());
			}
		}
		
		File info = new File(recDir.getAbsolutePath() + File.separatorChar + "REMOVE UNDERSCORE TO ENABLE RECIPE LOADING - RECIPES WILL RESET TO DEFAULT OTHERWISE");
		try { info.createNewFile(); } catch(IOException e) { }
		
		MainRegistry.logger.info("Starting recipe init!");
		
		for(SerializableRecipe recipe : recipeHandlers) {
			
			recipe.deleteRecipes();
			
			File recFile = new File(recDir.getAbsolutePath() + File.separatorChar + recipe.getFileName());
			if(recFile.exists() && recFile.isFile()) {
				MainRegistry.logger.info("Reading recipe file " + recFile.getName());
				recipe.readRecipeFile(recFile);
				recipe.modified = true;
			} else {
				MainRegistry.logger.info("No recipe file found, registering defaults for " + recipe.getFileName());
				recipe.registerDefaults();
				
				File recTemplate = new File(recDir.getAbsolutePath() + File.separatorChar + "_" + recipe.getFileName());
				MainRegistry.logger.info("Writing template file " + recTemplate.getName());
				recipe.writeTemplateFile(recTemplate);
				recipe.modified = false;
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
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void readRecipeFile(File file) {
		
		try {
			JsonObject json = gson.fromJson(new FileReader(file), JsonObject.class);
			JsonArray recipes = json.get("recipes").getAsJsonArray();
			for(JsonElement recipe : recipes) {
				this.readRecipe(recipe);
			}
		} catch(FileNotFoundException ex) { }
	}
	
	/*
	 * JSON IO UTIL
	 */
	
	public static AStack readAStack(JsonArray array) {
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
	
	public static AStack[] readAStackArray(JsonArray array) {
		try {
			AStack[] items = new AStack[array.size()];
			for(int i = 0; i < items.length; i++) { items[i] = readAStack((JsonArray) array.get(i)); }
			return items;
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading stack array " + array.toString());
		return new AStack[0];
	}
	
	public static void writeAStack(AStack astack, JsonWriter writer) throws IOException {
		writer.beginArray();
		writer.setIndent("");
		if(astack instanceof ComparableStack) {
			ComparableStack comp = (ComparableStack) astack;
			writer.value("item");														//ITEM  identifier
			writer.value(Item.itemRegistry.getNameForObject(comp.toStack().getItem()));	//item name
			if(comp.stacksize != 1 || comp.meta > 0) writer.value(comp.stacksize);						//stack size
			if(comp.meta > 0) writer.value(comp.meta);									//metadata
		}
		if(astack instanceof OreDictStack) {
			OreDictStack ore = (OreDictStack) astack;
			writer.value("dict");			//DICT identifier
			writer.value(ore.name);			//dict name
			if(ore.stacksize != 1) writer.value(ore.stacksize);	//stacksize
		}
		writer.endArray();
		writer.setIndent("  ");
	}
	
	public static ItemStack readItemStack(JsonArray array) {
		try {
			Item item = (Item) Item.itemRegistry.getObject(array.get(0).getAsString());
			int stacksize = array.size() > 1 ? array.get(1).getAsInt() : 1;
			int meta = array.size() > 2 ? array.get(2).getAsInt() : 0;
			if(item != null) return new ItemStack(item, stacksize, meta);
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading stack array " + array.toString() + " - defaulting to NOTHING item!");
		return new ItemStack(ModItems.nothing);
	}
	
	public static Pair<ItemStack, Float> readItemStackChance(JsonArray array) {
		try {
			Item item = (Item) Item.itemRegistry.getObject(array.get(0).getAsString());
			int stacksize = array.size() > 2 ? array.get(1).getAsInt() : 1;
			int meta = array.size() > 3 ? array.get(2).getAsInt() : 0;
			float chance = array.get(array.size() - 1).getAsFloat();
			if(item != null) return new Pair(new ItemStack(item, stacksize, meta), chance);
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading stack array " + array.toString() + " - defaulting to NOTHING item!");
		return new Pair(new ItemStack(ModItems.nothing), 1F);
	}
	
	public static ItemStack[] readItemStackArray(JsonArray array) {
		try {
			ItemStack[] items = new ItemStack[array.size()];
			for(int i = 0; i < items.length; i++) { items[i] = readItemStack((JsonArray) array.get(i)); }
			return items;
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading stack array " + array.toString());
		return new ItemStack[0];
	}
	
	public static Pair<ItemStack, Float>[] readItemStackArrayChance(JsonArray array) {
		try {
			Pair<ItemStack, Float>[] items = new Pair[array.size()];
			for(int i = 0; i < items.length; i++) { items[i] = readItemStackChance((JsonArray) array.get(i)); }
			return items;
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading stack array " + array.toString());
		return new Pair[0];
	}
	
	public static void writeItemStack(ItemStack stack, JsonWriter writer) throws IOException {
		writer.beginArray();
		writer.setIndent("");
		writer.value(Item.itemRegistry.getNameForObject(stack.getItem()));						//item name
		if(stack.stackSize != 1 || stack.getItemDamage() != 0) writer.value(stack.stackSize);	//stack size
		if(stack.getItemDamage() != 0) writer.value(stack.getItemDamage());						//metadata
		writer.endArray();
		writer.setIndent("  ");
	}
	
	public static void writeItemStackChance(Pair<ItemStack, Float> stack, JsonWriter writer) throws IOException {
		writer.beginArray();
		writer.setIndent("");
		writer.value(Item.itemRegistry.getNameForObject(stack.getKey().getItem()));											//item name
		if(stack.getKey().stackSize != 1 || stack.getKey().getItemDamage() != 0) writer.value(stack.getKey().stackSize);	//stack size
		if(stack.getKey().getItemDamage() != 0) writer.value(stack.getKey().getItemDamage());								//metadata
		writer.value(stack.value);																							//chance
		writer.endArray();
		writer.setIndent("  ");
	}
	
	public static FluidStack readFluidStack(JsonArray array) {
		try {
			FluidType type = Fluids.fromName(array.get(0).getAsString());
			int fill = array.get(1).getAsInt();
			int pressure = array.size() < 3 ? 0 : array.get(2).getAsInt();
			return new FluidStack(type, fill, pressure);
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading fluid array " + array.toString());
		return new FluidStack(Fluids.NONE, 0);
	}
	
	public static FluidStack[] readFluidArray(JsonArray array) {
		try {
			FluidStack[] fluids = new FluidStack[array.size()];
			for(int i = 0; i < fluids.length; i++) { fluids[i] = readFluidStack((JsonArray) array.get(i)); }
			return fluids;
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading fluid array " + array.toString());
		return new FluidStack[0];
	}
	
	public static void writeFluidStack(FluidStack stack, JsonWriter writer) throws IOException {
		writer.beginArray();
		writer.setIndent("");
		writer.value(stack.type.getName());	//fluid type
		writer.value(stack.fill);			//amount in mB
		if(stack.pressure != 0) writer.value(stack.pressure);
		writer.endArray();
		writer.setIndent("  ");
	}
	
	public static boolean matchesIngredients(ItemStack[] inputs, AStack[] recipe) {

		List<AStack> recipeList = new ArrayList();
		for(AStack ingredient : recipe) recipeList.add(ingredient);
		
		for(int i = 0; i < inputs.length; i++) {
			ItemStack inputStack = inputs[i];

			if(inputStack != null) {
				boolean hasMatch = false;
				Iterator<AStack> iterator = recipeList.iterator();

				while(iterator.hasNext()) {
					AStack recipeStack = iterator.next();

					if(recipeStack.matchesRecipe(inputStack, true) && inputStack.stackSize >= recipeStack.stacksize) {
						hasMatch = true;
						recipeList.remove(recipeStack);
						break;
					}
				}
				if(!hasMatch) {
					return false;
				}
			}
		}
		return recipeList.isEmpty();
	}
}
