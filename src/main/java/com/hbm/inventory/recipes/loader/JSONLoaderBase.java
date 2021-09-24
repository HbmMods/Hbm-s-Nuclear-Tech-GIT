package com.hbm.inventory.recipes.loader;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.main.MainRegistry;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public abstract class JSONLoaderBase {
	
	protected static AStack aStackFromArray(JsonArray array) {
		
		boolean dict = false;
		String item = "";
		int stacksize = 1;
		int meta = 0;
		
		if(array.size() < 2)
			return null;
		
		/*
		 * EVAL "dict" OR "item"
		 */
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
		
		/*
		 * EVAL NAME
		 */
		if(array.get(1).isJsonPrimitive()) {
			item = array.get(1).getAsString();
		} else {
			MainRegistry.logger.error("Error reading recipe, item string is not a valid data type!");
			return null;
		}
		
		/*
		 * EVAL STACKSIZE
		 */
		if(array.size() > 2 && array.get(2).isJsonPrimitive()) {
			if(array.get(2).getAsJsonPrimitive().isNumber()) {
				stacksize = Math.max(1, array.get(2).getAsJsonPrimitive().getAsNumber().intValue());
			} else {
				MainRegistry.logger.error("Error reading recipe, stack size is not a valid data type!");
				return null;
			}
		}
		
		/*
		 * RESOLVE OREDICT
		 */
		if(dict) {
			
			if(OreDictionary.doesOreNameExist(item)) {
				return new OreDictStack(item, stacksize);
			} else {
				
				MainRegistry.logger.error("Error reading recipe, ore dict name does not exist!");
				return null;
			}
		
		/*
		 * RESOLVE COMPARABLE
		 */
		} else {
			
			/*
			 * EVAL META
			 */
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
}
