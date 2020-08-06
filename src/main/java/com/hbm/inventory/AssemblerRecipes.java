package com.hbm.inventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.main.MainRegistry;

public class AssemblerRecipes {

	public static File config;
	public static File template;
	private static final Gson gson = new Gson();
	public static HashMap<ComparableStack, Object[]> recipes = new HashMap();
	public static HashMap<ComparableStack, Integer> time = new HashMap();
	
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
	}
	
	private static void registerDefaults() {
		
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
		return null;
	}
	
	public static void saveJSONRecipes() {
		
	}
	
	public static void saveTemplateJSON() {
		
		//TODO: pending
	}
}
