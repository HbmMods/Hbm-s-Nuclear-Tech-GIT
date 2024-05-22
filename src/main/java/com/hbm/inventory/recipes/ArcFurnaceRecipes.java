package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class ArcFurnaceRecipes extends SerializableRecipe {
	
	public static HashMap<AStack, ArcFurnaceRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		recipes.put(new OreDictStack(KEY_SAND),			new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(1))));
		recipes.put(new OreDictStack(QUARTZ.gem()),		new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 3))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(3))));
		recipes.put(new OreDictStack(QUARTZ.dust()),	new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 3))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(3))));
		recipes.put(new OreDictStack(QUARTZ.block()),	new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 12))	.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(12))));
		recipes.put(new OreDictStack(FIBER.ingot()),	new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 4))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.INGOT.q(1, 2))));
		recipes.put(new OreDictStack(FIBER.block()),	new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 40))	.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.INGOT.q(9, 2))));
		
		// Autogen for simple single type items
		for(NTMMaterial material : Mats.orderedList) {
			int in = material.convIn;
			int out = material.convOut;
			NTMMaterial convert = material.smeltsInto;
			if(convert.smeltable == SmeltingBehavior.SMELTABLE) {
				for(MaterialShapes shape : MaterialShapes.allShapes) {
					String name = shape.name() + material.names[0];
					if(!OreDictionary.getOres(name).isEmpty()) {
						OreDictStack dict = new OreDictStack(name);
						ArcFurnaceRecipe recipe = recipes.get(dict);
						if(recipe == null) recipe = new ArcFurnaceRecipe();
						if(recipe.fluidOutput == null) {
							recipe.fluid(new MaterialStack(convert, (int) (shape.q(1) * out / in)));
							recipes.put(dict, recipe);
						}
					}
				}
			}
		}
		
		// Autogen for custom smeltables
		for(Entry<String, List<MaterialStack>> entry : Mats.materialOreEntries.entrySet()) {
			OreDictStack dict = new OreDictStack(entry.getKey());
			addCustomSmeltable(dict, entry.getValue());
		}
		for(Entry<ComparableStack, List<MaterialStack>> entry : Mats.materialEntries.entrySet()) {
			addCustomSmeltable(entry.getKey(), entry.getValue());
		}
		
		// Autogen for furnace recipes
		for(Object o : FurnaceRecipes.smelting().getSmeltingList().entrySet()) {
			Entry entry = (Entry) o;
			ItemStack input = (ItemStack) entry.getKey();
			ItemStack output = (ItemStack) entry.getValue();
			
			ComparableStack comp = new ComparableStack(input);
			if(OreDictManager.arcSmeltable.contains(comp) || OreDictManager.arcSmeltable.contains(new ComparableStack(output))) {
				ArcFurnaceRecipe recipe = recipes.get(comp);
				if(recipe == null) recipe = new ArcFurnaceRecipe();
				if(recipe.solidOutput == null) {
					recipe.solid(output.copy());
					recipes.put(comp, recipe);
				}
			}
		}
	}
	
	private static void addCustomSmeltable(AStack astack, List<MaterialStack> mats) {
		List<MaterialStack> smeltables = new ArrayList();
		for(MaterialStack mat : mats) {
			if(mat.material.smeltable == SmeltingBehavior.SMELTABLE) {
				smeltables.add(mat);
			}
		}
		ArcFurnaceRecipe recipe = recipes.get(astack);
		if(recipe == null) recipe = new ArcFurnaceRecipe();
		if(recipe.fluidOutput == null) {
			recipe.fluid(smeltables.toArray(new MaterialStack[0]));
			recipes.put(astack, recipe);
		}
	}
	
	public static ArcFurnaceRecipe getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null) return null;
		
		ComparableStack comp = new ComparableStack(stack).makeSingular();

		if(recipes.containsKey(comp)) return recipes.get(comp);
		comp.meta = OreDictionary.WILDCARD_VALUE;
		if(recipes.containsKey(comp)) return recipes.get(comp);
		
		for(Entry<AStack, ArcFurnaceRecipe> entry : recipes.entrySet()) {
			if(entry.getKey().matchesRecipe(stack, true)) return entry.getValue();
		}
		
		return null;
	}

	@Override
	public String getFileName() {
		return "hbmArcFurnace.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		// TBI
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<AStack, ArcFurnaceRecipe> rec = (Entry<AStack, ArcFurnaceRecipe>) recipe;

		writer.name("input");
		this.writeAStack(rec.getKey(), writer);
		
		if(rec.getValue().solidOutput != null) {
			writer.name("solid");
			this.writeItemStack(rec.getValue().solidOutput, writer);
		}
		
		if(rec.getValue().fluidOutput != null) {
			writer.name("fluid").beginArray();
			writer.setIndent("");
			for(MaterialStack stack : rec.getValue().fluidOutput) {
				writer.beginArray();
				writer.value(stack.material.names[0]).value(stack.amount);
				writer.endArray();
			}
			writer.endArray();
			writer.setIndent("  ");
		}
	}

	public static class ArcFurnaceRecipe {

		public MaterialStack[] fluidOutput;
		public ItemStack solidOutput;
		
		public ArcFurnaceRecipe fluid(MaterialStack... outputs) {
			this.fluidOutput = outputs;
			return this;
		}
		
		public ArcFurnaceRecipe solid(ItemStack output) {
			this.solidOutput = output;
			return this;
		}
	}
}
