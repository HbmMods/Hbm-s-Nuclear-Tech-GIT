package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
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
import com.hbm.items.machine.ItemScraps;
import com.hbm.items.special.ItemBedrockOreNew;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreGrade;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;
import com.hbm.util.Tuple.Pair;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class ArcFurnaceRecipes extends SerializableRecipe {

	public static List<Pair<AStack, ArcFurnaceRecipe>> recipeList = new ArrayList();
	/* quick lookup for translating input stacks into the output, created lazily whenever a recipe is checked for the first time */
	public static HashMap<ComparableStack, ArcFurnaceRecipe> fastCacheSolid = new HashMap();
	public static HashMap<ComparableStack, ArcFurnaceRecipe> fastCacheLiquid = new HashMap();
	/* used for the recipe creation process to cache which inputs are already in use to prevent input collisions */
	public static HashSet<ComparableStack> occupiedSolid = new HashSet();
	public static HashSet<ComparableStack> occupiedLiquid = new HashSet();

	@Override
	public void registerDefaults() {

		register(new OreDictStack(KEY_SAND),			new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(1))));
		register(new ComparableStack(Items.flint),		new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 4))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.INGOT.q(1, 2))));
		register(new OreDictStack(QUARTZ.gem()),		new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 3))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(3))));
		register(new OreDictStack(QUARTZ.dust()),		new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 3))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(3))));
		register(new OreDictStack(QUARTZ.block()),		new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 12))	.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(12))));
		register(new OreDictStack(FIBER.ingot()),		new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 4))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.INGOT.q(1, 2))));
		register(new OreDictStack(FIBER.block()),		new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 40))	.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.INGOT.q(9, 2))));
		register(new OreDictStack(ASBESTOS.ingot()),	new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 4))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.INGOT.q(1, 2))));
		register(new OreDictStack(ASBESTOS.dust()),		new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 4))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.INGOT.q(1, 2))));
		register(new OreDictStack(ASBESTOS.block()),	new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 40))	.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.INGOT.q(9, 2))));

		register(new ComparableStack(ModBlocks.sand_quartz), new ArcFurnaceRecipe().solid(new ItemStack(ModBlocks.glass_quartz)));
		register(new OreDictStack(BORAX.dust()), new ArcFurnaceRecipe().solid(new ItemStack(ModItems.powder_boron_tiny, 3)).fluid(new MaterialStack(Mats.MAT_BORON, MaterialShapes.NUGGET.q(3))));
		
		for(BedrockOreType type : BedrockOreType.values()) {
			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SULFURIC_BYPRODUCT, type)), new ArcFurnaceRecipe().solid(ItemBedrockOreNew.make(BedrockOreGrade.SULFURIC_ARC, type, 2)));
			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SULFURIC_ROASTED, type)), new ArcFurnaceRecipe().solid(ItemBedrockOreNew.make(BedrockOreGrade.SULFURIC_ARC, type, 4)));
			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SOLVENT_BYPRODUCT, type)), new ArcFurnaceRecipe().solid(ItemBedrockOreNew.make(BedrockOreGrade.SOLVENT_ARC, type, 2)));
			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SOLVENT_ROASTED, type)), new ArcFurnaceRecipe().solid(ItemBedrockOreNew.make(BedrockOreGrade.SOLVENT_ARC, type, 4)));
			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.RAD_BYPRODUCT, type)), new ArcFurnaceRecipe().solid(ItemBedrockOreNew.make(BedrockOreGrade.RAD_ARC, type, 2)));
			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.RAD_ROASTED, type)), new ArcFurnaceRecipe().solid(ItemBedrockOreNew.make(BedrockOreGrade.RAD_ARC, type, 4)));

			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY_FIRST, type)), new ArcFurnaceRecipe().fluidNull(ItemBedrockOreNew.toFluid(type.primary1, 5), ItemBedrockOreNew.toFluid(type.primary2, 2)));
			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY_SECOND, type)), new ArcFurnaceRecipe().fluidNull(ItemBedrockOreNew.toFluid(type.primary1, 2), ItemBedrockOreNew.toFluid(type.primary2, 5)));
			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.CRUMBS, type)), new ArcFurnaceRecipe().fluidNull(ItemBedrockOreNew.toFluid(type.primary1, 1), ItemBedrockOreNew.toFluid(type.primary2, 1)));
			
			int i3 = 3;
			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SULFURIC_WASHED, type)), new ArcFurnaceRecipe().fluidNull(ItemBedrockOreNew.toFluid(type.byproductAcid1, i3), ItemBedrockOreNew.toFluid(type.byproductAcid2, i3), ItemBedrockOreNew.toFluid(type.byproductAcid3, i3)));
			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SOLVENT_WASHED, type)), new ArcFurnaceRecipe().fluidNull(ItemBedrockOreNew.toFluid(type.byproductSolvent1, i3), ItemBedrockOreNew.toFluid(type.byproductSolvent2, i3), ItemBedrockOreNew.toFluid(type.byproductSolvent3, i3)));
			register(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.RAD_WASHED, type)), new ArcFurnaceRecipe().fluidNull(ItemBedrockOreNew.toFluid(type.byproductRad1, i3), ItemBedrockOreNew.toFluid(type.byproductRad2, i3), ItemBedrockOreNew.toFluid(type.byproductRad3, i3)));
		}
		
		// Autogen for simple single type items
		for(NTMMaterial material : Mats.orderedList) {
			int in = material.convIn;
			int out = material.convOut;
			NTMMaterial convert = material.smeltsInto;
			if(convert.smeltable == SmeltingBehavior.SMELTABLE) {
				for(MaterialShapes shape : MaterialShapes.allShapes) {
					if(!shape.noAutogen) {
						String name = shape.name() + material.names[0];
						if(!OreDictionary.getOres(name).isEmpty()) {
							OreDictStack dict = new OreDictStack(name);
							ArcFurnaceRecipe recipe = new ArcFurnaceRecipe();
							recipe.fluid(new MaterialStack(convert, (int) (shape.q(1) * out / in)));
							register(dict, recipe);
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
		registerFurnaceSmeltables();
	}
	
	public static void registerFurnaceSmeltables() {
		for(Object o : FurnaceRecipes.smelting().getSmeltingList().entrySet()) {
			Entry entry = (Entry) o;
			ItemStack input = (ItemStack) entry.getKey();
			ItemStack output = (ItemStack) entry.getValue();
			
			if(input != null && output != null) {
				ComparableStack comp = new ComparableStack(input);
				if(OreDictManager.arcSmeltable.contains(comp) || OreDictManager.arcSmeltable.contains(new ComparableStack(output))) {
					ArcFurnaceRecipe recipe = new ArcFurnaceRecipe();
					recipe.solid(output.copy());
					register(comp, recipe);
				}
			}
		}
	}
	
	public static void register(AStack input, ArcFurnaceRecipe output) {
		List<ItemStack> inputs = input.extractForNEI();
		for(ItemStack stack : inputs) {
			ComparableStack compStack = new ComparableStack(stack);
			if(compStack.meta == OreDictionary.WILDCARD_VALUE) compStack.meta = 0;
			if(output.solidOutput != null) if(occupiedSolid.contains(compStack)) return;
			if(output.fluidOutput != null) if(occupiedLiquid.contains(compStack)) return;
		}
		recipeList.add(new Pair(input, output));
		for(ItemStack stack : inputs) {
			ComparableStack compStack = new ComparableStack(stack);
			if(compStack.meta == OreDictionary.WILDCARD_VALUE) compStack.meta = 0;
			if(output.solidOutput != null) occupiedSolid.add(compStack);
			if(output.fluidOutput != null) occupiedLiquid.add(compStack);
		}
	}
	
	private static void addCustomSmeltable(AStack astack, List<MaterialStack> mats) {
		List<MaterialStack> smeltables = new ArrayList();
		for(MaterialStack mat : mats) {
			if(mat.material.smeltable == SmeltingBehavior.SMELTABLE) {
				smeltables.add(mat);
			}
		}
		if(smeltables.isEmpty()) return;
		ArcFurnaceRecipe recipe = new ArcFurnaceRecipe();
		recipe.fluid(smeltables.toArray(new MaterialStack[0]));
		register(astack, recipe);
	}
	
	public static ArcFurnaceRecipe getOutput(ItemStack stack, boolean liquid) {
		
		if(stack == null || stack.getItem() == null) return null;
		
		if(stack.getItem() == ModItems.scraps && liquid) {
			NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
			if(mat == null) return null;
			MaterialStack mats = ItemScraps.getMats(stack);
			if(mats.material.smeltable == SmeltingBehavior.SMELTABLE) {
				return new ArcFurnaceRecipe().fluid(mats);
			}
		}
		
		ComparableStack cacheKey = new ComparableStack(stack).makeSingular();
		if(!liquid && fastCacheSolid.containsKey(cacheKey)) return fastCacheSolid.get(cacheKey);
		if(liquid && fastCacheLiquid.containsKey(cacheKey)) return fastCacheLiquid.get(cacheKey);
		
		for(Pair<AStack, ArcFurnaceRecipe> entry : recipeList) {
			if(entry.getKey().matchesRecipe(stack, true)) {
				ArcFurnaceRecipe rec = entry.getValue();
				if((liquid && rec.fluidOutput != null) || (!liquid && rec.solidOutput != null)) {
					if(!liquid) fastCacheSolid.put(cacheKey, rec);
					if(liquid) fastCacheLiquid.put(cacheKey, rec);
					return rec;
				}
			}
		}
		
		if(!liquid) fastCacheSolid.put(cacheKey, null);
		if(liquid) fastCacheLiquid.put(cacheKey, null);
		
		return null;
	}

	public static HashMap getSolidRecipes() {
		HashMap<Object, Object> recipes = new HashMap<Object, Object>();
		for(Pair<AStack, ArcFurnaceRecipe> recipe : ArcFurnaceRecipes.recipeList) {
			if(recipe.getValue().solidOutput != null) recipes.put(recipe.getKey().copy(), recipe.getValue().solidOutput.copy());
		}
		return recipes;
	}

	public static HashMap getFluidRecipes() {
		HashMap<Object, Object> recipes = new HashMap<Object, Object>();
		for(Pair<AStack, ArcFurnaceRecipe> recipe : ArcFurnaceRecipes.recipeList) {
			if(recipe.getValue().fluidOutput != null && recipe.getValue().fluidOutput.length > 0) {
				Object[] out = new Object[recipe.getValue().fluidOutput.length];
				for(int i = 0; i < out.length; i++) out[i] = ItemScraps.create(recipe.getValue().fluidOutput[i], true);
				recipes.put(recipe.getKey().copy(), out);
			}
		}
		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.smeltable == SmeltingBehavior.SMELTABLE) {
				recipes.put(new ItemStack(ModItems.scraps, 1, mat.id), ItemScraps.create(new MaterialStack(mat, MaterialShapes.INGOT.q(1)), true));
			}
		}
		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmArcFurnace.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipeList;
	}

	@Override
	public void deleteRecipes() {
		occupiedSolid.clear();
		occupiedLiquid.clear();
		recipeList.clear();
		fastCacheSolid.clear();
		fastCacheLiquid.clear();
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject rec = (JsonObject) recipe;
		ArcFurnaceRecipe arc = new ArcFurnaceRecipe();
		
		AStack input = this.readAStack(rec.get("input").getAsJsonArray());
		
		if(rec.has("solid")) {
			arc.solid(this.readItemStack(rec.get("solid").getAsJsonArray()));
		}
		
		if(rec.has("fluid")) {
			JsonArray fluids = rec.get("fluid").getAsJsonArray();
			List<MaterialStack> mats = new ArrayList();
			for(JsonElement fluid : fluids) {
				JsonArray matStack = fluid.getAsJsonArray();
				MaterialStack stack = new MaterialStack(Mats.matByName.get(matStack.get(0).getAsString()), matStack.get(1).getAsInt());
				if(stack.material.smeltable == SmeltingBehavior.SMELTABLE) {
					mats.add(stack);
				}
			}
			if(!mats.isEmpty()) {
				arc.fluid(mats.toArray(new MaterialStack[0]));
			}
		}
		
		register(input, arc);
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Pair<AStack, ArcFurnaceRecipe> rec = (Pair<AStack, ArcFurnaceRecipe>) recipe;

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
		
		public ArcFurnaceRecipe fluidNull(MaterialStack... outputs) {
			List<MaterialStack> mat = new ArrayList();
			for(MaterialStack stack : outputs) if(stack != null) mat.add(stack);
			if(!mat.isEmpty()) this.fluidOutput = mat.toArray(new MaterialStack[0]);
			return this;
		}
		
		public ArcFurnaceRecipe solid(ItemStack output) {
			this.solidOutput = output;
			return this;
		}
	}
}
