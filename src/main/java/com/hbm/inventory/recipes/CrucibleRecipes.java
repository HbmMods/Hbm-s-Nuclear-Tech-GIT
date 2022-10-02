package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import com.hbm.items.machine.ItemMold.Mold;
import com.hbm.items.machine.ItemScraps;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CrucibleRecipes extends SerializableRecipe {

	public static HashMap<Integer, CrucibleRecipe> indexMapping = new HashMap();
	public static List<CrucibleRecipe> recipes = new ArrayList();
	
	/*
	 * IMPORTANT: crucibles do not have stack size checks for the recipe's result, meaning that they can overflow if the resulting stacks are
	 * bigger than the input stacks, so make sure that material doesn't "expand". very few things do that IRL when alloying anyway.
	 */
	
	@Override
	public void registerDefaults() {

		int n = MaterialShapes.NUGGET.q(1);
		int i = MaterialShapes.INGOT.q(1);
		
		recipes.add(new CrucibleRecipe(0, "crucible.steel", 1, new ItemStack(ModItems.ingot_steel))
				.inputs(new MaterialStack(Mats.MAT_IRON, n), new MaterialStack(Mats.MAT_COAL, n))
				.outputs(new MaterialStack(Mats.MAT_STEEL, n)));
		
		recipes.add(new CrucibleRecipe(1, "crucible.redcopper", 2, new ItemStack(ModItems.ingot_red_copper))
				.inputs(new MaterialStack(Mats.MAT_COPPER, n), new MaterialStack(Mats.MAT_REDSTONE, n))
				.outputs(new MaterialStack(Mats.MAT_MINGRADE, n * 2)));
		
		recipes.add(new CrucibleRecipe(2, "crucible.aa", 2, new ItemStack(ModItems.ingot_advanced_alloy))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n), new MaterialStack(Mats.MAT_MINGRADE, n))
				.outputs(new MaterialStack(Mats.MAT_ALLOY, n * 2)));
		
		recipes.add(new CrucibleRecipe(3, "crucible.hss", 4, new ItemStack(ModItems.ingot_dura_steel))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n * 2), new MaterialStack(Mats.MAT_TUNGSTEN, n), new MaterialStack(Mats.MAT_COBALT, n))
				.outputs(new MaterialStack(Mats.MAT_DURA, n * 4)));
		
		recipes.add(new CrucibleRecipe(4, "crucible.ferro", 3, new ItemStack(ModItems.ingot_ferrouranium))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n * 2), new MaterialStack(Mats.MAT_U238, n), new MaterialStack(Mats.MAT_COAL, n))
				.outputs(new MaterialStack(Mats.MAT_FERRO, n * 3)));
		
		recipes.add(new CrucibleRecipe(5, "crucible.tcalloy", 9, new ItemStack(ModItems.ingot_tcalloy))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n * 8), new MaterialStack(Mats.MAT_TECHNIETIUM, n), new MaterialStack(Mats.MAT_COAL, n * 4))
				.outputs(new MaterialStack(Mats.MAT_TCALLOY, i)));
		
		registerMoldsForNEI();
	}

	public static class CrucibleRecipe {
		public MaterialStack[] input;
		public MaterialStack[] output;
		private int id;
		private String name;
		public int frequency = 1;
		public ItemStack icon;
		
		public CrucibleRecipe(int id, String name, int frequency, ItemStack icon) {
			this.id = id;
			this.name = name;
			this.frequency = frequency;
			this.icon = icon;
			
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
	public String getComment() {
		return "/// under construction ///";
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
	
	/** Returns a map containing all recipes where an item becomes a liquid material in the crucible. */
	public static HashMap<AStack, List<ItemStack>> getSmeltingRecipes() {
		HashMap<AStack, List<ItemStack>> map = new HashMap();
		
		for(NTMMaterial material : Mats.orderedList) {
			for(MaterialShapes shape : MaterialShapes.values()) {
				//TODO: buffer these
				
				String name = shape.toString().toLowerCase() + material.names[0];
				List<ItemStack> ores = OreDictionary.getOres(name);
				
				if(!ores.isEmpty()) {
					List<ItemStack> stacks = new ArrayList();
					stacks.add(ItemScraps.create(new MaterialStack(material, shape.q(1))));
					map.put(new OreDictStack(name), stacks);
				}
			}
		}
		
		for(Entry<String, List<MaterialStack>> entry : Mats.materialOreEntries.entrySet()) {
			List<ItemStack> stacks = new ArrayList();
			for(MaterialStack mat : entry.getValue()) {
				stacks.add(ItemScraps.create(mat));
			}
			map.put(new OreDictStack(entry.getKey()), stacks);
		}
		
		for(Entry<ComparableStack, List<MaterialStack>> entry : Mats.materialEntries.entrySet()) {
			List<ItemStack> stacks = new ArrayList();
			for(MaterialStack mat : entry.getValue()) {
				stacks.add(ItemScraps.create(mat));
			}
			map.put(entry.getKey().copy(), stacks);
		}
		
		return map;
	}
	
	public static List<ItemStack[]> moldRecipes = new ArrayList();
	
	public static void registerMoldsForNEI() {
		
		for(NTMMaterial material : Mats.orderedList) {
			
			if(material.smeltable != SmeltingBehavior.SMELTABLE)
				continue;
			
			for(Mold mold : ItemMold.molds) {
				ItemStack out = mold.getOutput(material);
				if(out != null) {
					ItemStack scrap = ItemScraps.create(new MaterialStack(material, mold.getCost()));
					ItemStack shape = new ItemStack(ModItems.mold, 1, mold.id);
					ItemStack basin = new ItemStack(mold.size == 0 ? ModBlocks.foundry_mold : mold.size == 1 ? ModBlocks.foundry_basin : Blocks.fire);
					ItemStack[] entry = new ItemStack[] {scrap, shape, basin, out};
					moldRecipes.add(entry);
				}
			}
		}
	}
}
