package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;

public class CrucibleRecipes extends SerializableRecipe {

	public static HashMap<Integer, CrucibleRecipe> indexMapping = new HashMap();
	public static List<CrucibleRecipe> recipes = new ArrayList();


	@Override
	public void registerDefaults() {
		
		recipes.add(new CrucibleRecipe(0, "crucible.steel", 1)
				.inputs(new MaterialStack(Mats.MAT_IRON, 10), new MaterialStack(Mats.MAT_COAL, 15))
				.outputs(new MaterialStack(Mats.MAT_STEEL, 10)));
		
		recipes.add(new CrucibleRecipe(1, "crucible.redcopper", 1)
				.inputs(new MaterialStack(Mats.MAT_STEEL, 10), new MaterialStack(Mats.MAT_REDSTONE, 10))
				.outputs(new MaterialStack(Mats.MAT_MINGRADE, 20)));
		
		recipes.add(new CrucibleRecipe(2, "crucible.aa", 1)
				.inputs(new MaterialStack(Mats.MAT_STEEL, 10), new MaterialStack(Mats.MAT_MINGRADE, 10))
				.outputs(new MaterialStack(Mats.MAT_ALLOY, 20)));
	}

	public static class CrucibleRecipe {
		public MaterialStack[] input;
		public MaterialStack[] output;
		private int id;
		private String name;
		public int frequency;
		
		public CrucibleRecipe(int id, String name, int frequency) {
			this.id = id;
			this.name = name;
			this.frequency = frequency;
			
			if(!indexMapping.containsKey(id)) {
				indexMapping.put(id, this);
			} else {
				throw new IllegalStateException("Crucible recipe " + name + " has been registered with duplicate id " + id + " used by " + indexMapping.get(id).name + "!");
			}
		}
		
		public CrucibleRecipe inputs(MaterialStack... input) {
			this.input = input;
			return this;
		}
		
		public CrucibleRecipe outputs(MaterialStack... output) {
			this.output = output;
			return this;
		}
		
		public int getId() {
			return this.id;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getInputAmount() {
			
			int content = 0;
			
			for(MaterialStack stack : input) {
				content += stack.amount;
			}
			
			return content;
		}
	}

	@Override
	public String getFileName() {
		return "hbmCrucible.json";
	}

	@Override
	public Object getRecipeObject() {
		return this.recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		
	}

	@Override
	public void deleteRecipes() {
		this.indexMapping.clear();
		this.recipes.clear();
	}
}
