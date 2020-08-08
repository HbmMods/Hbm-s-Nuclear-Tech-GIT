package com.hbm.inventory;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hbm.interfaces.Untested;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

@Untested
public class AssemblerRecipes {

	public static File config;
	public static File template;
	private static final Gson gson = new Gson();
	public static HashMap<ComparableStack, Object[]> recipes = new HashMap();
	public static HashMap<ComparableStack, Integer> time = new HashMap();
	public static List<ComparableStack> recipeList = new ArrayList();
	
	/**
	 * Pre-Init phase: Finds the recipe config (if exists) and checks if a template is present, if not it generates one.
	 * @param dir The suggested config folder
	 */
	public static void preInit(File dir) {
		
		if(dir == null || !dir.isDirectory())
			return;
		
		List<File> files = Arrays.asList(dir.listFiles());
		
		boolean needsTemplate = true;
		
		for(File file : files) {
			if(file.getName().equals("hbmAssembler.json")) {
				
				config = file;
			}
			
			if(file.getName().equals("hbmAssemblerTemplate.json")) {
				
				needsTemplate = false;
			}
		}
		
		if(needsTemplate)
			saveTemplateJSON();
	}
	
	public static void loadRecipes() {
		
		if(config == null)
			registerDefaults();
		else
			loadJSONRecipes();
		
		generateList();
	}
	
	/**
	 * Generates an ordered list of outputs, used by the template item to generate subitems
	 */
	private static void generateList() {
		
		List<ComparableStack> list = new ArrayList(recipes.keySet());
		Collections.sort(list);
		recipeList = list;
	}
	
	/**
	 * Registers regular recipes if there's no custom confiuration
	 */
	private static void registerDefaults() {

		makeRecipe(new ComparableStack(ModItems.plate_iron, 2),
				new Object[] {new OreDictStack("ingotIron", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.plate_gold, 2),
				new Object[] {new OreDictStack("ingotGold", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.plate_titanium, 2),
				new Object[] {new OreDictStack("ingotTitanium", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.plate_aluminium, 2),
				new Object[] {new OreDictStack("ingotAluminum", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.plate_steel, 2),
				new Object[] {new OreDictStack("ingotSteel", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.plate_lead, 2),
				new Object[] {new OreDictStack("ingotLead", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.asbestos_cloth, 4),
				new Object[] {new OreDictStack("ingotAsbestos", 2), new ComparableStack(Items.string, 6)},
				20);
	}
	
	private static void makeRecipe(ComparableStack out, Object[] in, int duration) {
		
		recipes.put(out, in);
		time.put(out, duration);
	}
	
	/*
	 * recipes : [
	 *   {
	 *     output : [ "item", "hbm:tank_steel", 1, 0 ],
	 *     duration : 100,
	 *     input : [
	 *       [ "dict", "plateSteel", 6 ],
	 *       [ "dict", "plateTitanium", 2 ],
	 *       [ "item", "dye", 1, 15 ],
	 *     ]
	 *   },
	 *   {
	 *     output : [ "item", "hbm:plate_gold", 2, 0 ],
	 *     duration : 20,
	 *     input : [
	 *       [ "dict", "ingotGold", 3 ]
	 *     ]
	 *   }
	 * ]
	 */
	private static void loadJSONRecipes() {
		
		try {
			JsonObject json = gson.fromJson(new FileReader(config), JsonObject.class);
			
			JsonElement recipes = json.get("recipes");
			
			if(recipes instanceof JsonArray) {
				
				JsonArray recArray = recipes.getAsJsonArray();
				
				//go through the recipes array
				for(JsonElement recipe : recArray) {
					
					if(recipe.isJsonObject()) {
						
						JsonObject recObj = recipe.getAsJsonObject();
						
						JsonElement input = recObj.get("input");
						JsonElement output = recObj.get("output");
						JsonElement duration = recObj.get("duration");
						
						int time = 100;
						
						if(duration.isJsonPrimitive()) {
							if(duration.getAsJsonPrimitive().isNumber()) {
								time = Math.max(1, duration.getAsJsonPrimitive().getAsInt());
							}
						}
						
						if(!(input instanceof JsonArray)) {
							MainRegistry.logger.error("Error reading recipe, no input found!");
							continue;
						}
						
						if(!(output instanceof JsonArray)) {
							MainRegistry.logger.error("Error reading recipe, no output found!");
							continue;
						}
						
						Object outp = parseJsonArray(output.getAsJsonArray());
						List inp = new ArrayList();
						
						for(JsonElement in : input.getAsJsonArray()) {
							
							if(in.isJsonArray()) {
								Object i = parseJsonArray(in.getAsJsonArray());

								if(i instanceof ComparableStack || i instanceof OreDictStack)
									inp.add(i);
							}
						}
						
						if(outp instanceof ComparableStack) {
							AssemblerRecipes.recipes.put((ComparableStack) outp, inp.toArray());
							AssemblerRecipes.time.put((ComparableStack) outp, time);
						}
					}
				}
			}
			
		} catch (Exception e) {
			//shush
		}
	}
	
	private static Object parseJsonArray(JsonArray array) {
		
		boolean dict = false;
		String item = "";
		int stacksize = 1;
		int meta = 0;
		
		if(array.size() < 2)
			return null;
		
		//is index 0 "item" or "dict"?
		if(array.get(0).isJsonPrimitive()) {
			
			if(array.get(0).getAsString().equals("item")) {
				dict = false;
			} else if(array.get(0).getAsString().equals("dict")) {
				dict = true;
			} else {
				
				MainRegistry.logger.error("Error reading recipe, stack array does not have 'item' or 'dict' label!");
				return null;
			}
			
		} else {
			
			MainRegistry.logger.error("Error reading recipe, label is not a valid data type!");
			return null;
		}
		
		//is index 1 a string
		if(array.get(1).isJsonPrimitive()) {
			
			item = array.get(1).getAsString();
			
		} else {
			MainRegistry.logger.error("Error reading recipe, item string is not a valid data type!");
			return null;
		}
		
		//if index 3 exists, eval it as a stacksize
		if(array.size() > 2 && array.get(2).isJsonPrimitive()) {
			
			if(array.get(2).getAsJsonPrimitive().isNumber()) {
				
				stacksize = Math.min(64, Math.max(1, array.get(2).getAsJsonPrimitive().getAsNumber().intValue()));
				
			} else {
				
				MainRegistry.logger.error("Error reading recipe, stack size is not a valid data type!");
				return null;
			}
		}
		
		//ore dict implementation
		if(dict) {
			
			if(OreDictionary.doesOreNameExist(item)) {
				return new OreDictStack(item, stacksize);
			} else {
				
				MainRegistry.logger.error("Error reading recipe, ore dict name does not exist!");
				return null;
			}
		
		//comparable stack
		} else {
			
			//if index 4 exists, eval it as a meta
			if(array.size() > 3 && array.get(3).isJsonPrimitive()) {
				
				if(array.get(3).getAsJsonPrimitive().isNumber()) {
					
					meta = Math.max(0, array.get(3).getAsJsonPrimitive().getAsNumber().intValue());
					
				} else {
					
					MainRegistry.logger.error("Error reading recipe, metadata is not a valid data type!");
					return null;
				}
			}
			
			Item it = (Item)Item.itemRegistry.getObject(item);
			
			if(it == null) {
				
				MainRegistry.logger.error("Item could not be found!");
				return null;
			}
			
			return new ComparableStack(it, stacksize, meta);
		}
	}
	
	public static void saveJSONRecipes() {
		
	}
	
	public static void saveTemplateJSON() {
		
		//TODO: pending
	}
}
