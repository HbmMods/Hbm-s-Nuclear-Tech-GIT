package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.machine.ItemScraps;
import com.hbm.items.special.ItemBedrockOreNew;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreGrade;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;

public class ElectrolyserMetalRecipes extends SerializableRecipe {
	
	public static HashMap<AStack, ElectrolysisMetalRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		recipes.put(new ComparableStack(ModItems.crystal_iron), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_IRON, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_TITANIUM, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_lithium_tiny, 3)));
		
		recipes.put(new ComparableStack(ModItems.crystal_gold), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_GOLD, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_LEAD, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_lithium_tiny, 3),
				new ItemStack(ModItems.ingot_mercury, 2)));
		
		recipes.put(new ComparableStack(ModItems.crystal_uranium), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_URANIUM, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_RADIUM, MaterialShapes.NUGGET.q(4)),
				new ItemStack(ModItems.powder_lithium_tiny, 3)));
		
		recipes.put(new ComparableStack(ModItems.crystal_thorium), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_THORIUM, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_URANIUM, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_lithium_tiny, 3)));
		
		recipes.put(new ComparableStack(ModItems.crystal_plutonium), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_PLUTONIUM, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_POLONIUM, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_lithium_tiny, 3)));
		
		recipes.put(new ComparableStack(ModItems.crystal_titanium), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_TITANIUM, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_IRON, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_lithium_tiny, 3)));
		
		recipes.put(new ComparableStack(ModItems.crystal_copper), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_COPPER, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_LEAD, MaterialShapes.NUGGET.q(4)),
				new ItemStack(ModItems.powder_lithium_tiny, 3),
				new ItemStack(ModItems.sulfur, 2)));
		
		recipes.put(new ComparableStack(ModItems.crystal_tungsten), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_TUNGSTEN, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_IRON, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_lithium_tiny, 3)));
		
		recipes.put(new ComparableStack(ModItems.crystal_aluminium), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_ALUMINIUM, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_IRON, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_lithium_tiny, 3)));
		
		recipes.put(new ComparableStack(ModItems.crystal_beryllium), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_BERYLLIUM, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_LEAD, MaterialShapes.NUGGET.q(4)),
				new ItemStack(ModItems.powder_lithium_tiny, 3),
				new ItemStack(ModItems.powder_quartz, 2)));
		
		recipes.put(new ComparableStack(ModItems.crystal_lead), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_LEAD, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_GOLD, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_lithium_tiny, 3)));
		
		recipes.put(new ComparableStack(ModItems.crystal_schraranium), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_SCHRABIDIUM, MaterialShapes.NUGGET.q(5)),
				new MaterialStack(Mats.MAT_URANIUM, MaterialShapes.NUGGET.q(2)),
				new ItemStack(ModItems.nugget_neptunium, 2)));
		
		recipes.put(new ComparableStack(ModItems.crystal_schrabidium), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_SCHRABIDIUM, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_PLUTONIUM, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_lithium_tiny, 3)));
		
		recipes.put(new ComparableStack(ModItems.crystal_rare), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_ZIRCONIUM, MaterialShapes.NUGGET.q(6)),
				new MaterialStack(Mats.MAT_BORON, MaterialShapes.NUGGET.q(2)),
				new ItemStack(ModItems.powder_desh_mix, 3)));
		
		recipes.put(new ComparableStack(ModItems.crystal_trixite), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_PLUTONIUM, MaterialShapes.INGOT.q(3)),
				new MaterialStack(Mats.MAT_COBALT, MaterialShapes.INGOT.q(4)),
				new ItemStack(ModItems.powder_niobium, 4),
				new ItemStack(ModItems.powder_nitan_mix, 2)));
		
		recipes.put(new ComparableStack(ModItems.crystal_lithium), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_LITHIUM, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_BORON, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_quartz, 2),
				new ItemStack(ModItems.fluorite, 2)));
		
		recipes.put(new ComparableStack(ModItems.crystal_starmetal), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_DURA, MaterialShapes.INGOT.q(4)),
				new MaterialStack(Mats.MAT_COBALT, MaterialShapes.INGOT.q(4)),
				new ItemStack(ModItems.powder_astatine, 3),
				new ItemStack(ModItems.ingot_mercury, 8)));
		
		recipes.put(new ComparableStack(ModItems.crystal_cobalt), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_COBALT, MaterialShapes.INGOT.q(3)),
				new MaterialStack(Mats.MAT_IRON, MaterialShapes.INGOT.q(4)),
				new ItemStack(ModItems.powder_copper, 4),
				new ItemStack(ModItems.powder_lithium_tiny, 3)));
		
		for(BedrockOreType type : BedrockOreType.values()) {

			MaterialStack f0 = ItemBedrockOreNew.toFluid(type.primary1, MaterialShapes.INGOT.q(12));
			MaterialStack f1 = ItemBedrockOreNew.toFluid(type.primary2, MaterialShapes.INGOT.q(6));
			recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY_FIRST, type)), new ElectrolysisMetalRecipe(
					f0 != null ? f0 : new MaterialStack(Mats.MAT_SLAG, MaterialShapes.INGOT.q(1)),
					f1 != null ? f1 : new MaterialStack(Mats.MAT_SLAG, MaterialShapes.INGOT.q(1)),
					20,
					f0 == null ? ItemBedrockOreNew.extract(type.primary1, 12) : new ItemStack(ModItems.dust),
					f1 == null ? ItemBedrockOreNew.extract(type.primary2, 6) : new ItemStack(ModItems.dust),
					ItemBedrockOreNew.make(BedrockOreGrade.CRUMBS, type)));
			MaterialStack s0 = ItemBedrockOreNew.toFluid(type.primary1, MaterialShapes.INGOT.q(6));
			MaterialStack s1 = ItemBedrockOreNew.toFluid(type.primary2, MaterialShapes.INGOT.q(12));
			recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY_SECOND, type)), new ElectrolysisMetalRecipe(
					s0 != null ? s0 : new MaterialStack(Mats.MAT_SLAG, MaterialShapes.INGOT.q(1)),
					s1 != null ? s1 : new MaterialStack(Mats.MAT_SLAG, MaterialShapes.INGOT.q(1)),
					20,
					s0 == null ? ItemBedrockOreNew.extract(type.primary1, 6) : new ItemStack(ModItems.dust),
					s1 == null ? ItemBedrockOreNew.extract(type.primary2, 12) : new ItemStack(ModItems.dust),
					ItemBedrockOreNew.make(BedrockOreGrade.CRUMBS, type)));

			MaterialStack c0 = ItemBedrockOreNew.toFluid(type.primary1, MaterialShapes.INGOT.q(2));
			MaterialStack c1 = ItemBedrockOreNew.toFluid(type.primary2, MaterialShapes.INGOT.q(2));
			recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.CRUMBS, type)), new ElectrolysisMetalRecipe(
					c0 != null ? c0 : new MaterialStack(Mats.MAT_SLAG, MaterialShapes.INGOT.q(1, 2)),
					c1 != null ? c1 : new MaterialStack(Mats.MAT_SLAG, MaterialShapes.INGOT.q(1, 2)),
					20,
					c0 == null ? ItemBedrockOreNew.extract(type.primary1, 2) : new ItemStack(ModItems.dust),
					c1 == null ? ItemBedrockOreNew.extract(type.primary2, 2) : new ItemStack(ModItems.dust)));
		}
	}
	
	public static ElectrolysisMetalRecipe getRecipe(ItemStack stack) {
		if(stack == null || stack.getItem() == null)
			return null;

		ComparableStack comp = new ComparableStack(stack).makeSingular();
		
		if(recipes.containsKey(comp)) return recipes.get(comp);
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		for(String name : names) {
			OreDictStack ore = new OreDictStack(name);
			if(recipes.containsKey(ore)) return recipes.get(ore);
		}
		
		return null;
	}

	public static HashMap getRecipes() {
		
		HashMap<Object[], Object[]> recipes = new HashMap();
		
		for(Entry<AStack, ElectrolysisMetalRecipe> entry : ElectrolyserMetalRecipes.recipes.entrySet()) {
			
			ElectrolysisMetalRecipe recipe = entry.getValue();
			Object[] input = new Object[] { entry.getKey().copy(), ItemFluidIcon.make(Fluids.NITRIC_ACID, 100) };
			List outputs = new ArrayList();
			if(recipe.output1 != null) outputs.add(ItemScraps.create(recipe.output1, true));
			if(recipe.output2 != null) outputs.add(ItemScraps.create(recipe.output2, true));
			for(ItemStack byproduct : recipe.byproduct) outputs.add(byproduct);
			
			recipes.put(input, outputs.toArray());
		}
		
		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmElectrolyzerMetal.json";
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
		JsonObject obj = (JsonObject) recipe;
		
		AStack input = this.readAStack(obj.get("input").getAsJsonArray());

		JsonArray out1 = obj.get("output1").getAsJsonArray();
		String name1 = out1.get(0).getAsString();
		int amount1 = out1.get(1).getAsInt();
		MaterialStack output1 = new MaterialStack(Mats.matByName.get(name1), amount1);

		JsonArray out2 = obj.get("output2").getAsJsonArray();
		String name2 = out2.get(0).getAsString();
		int amount2 = out2.get(1).getAsInt();
		MaterialStack output2 = new MaterialStack(Mats.matByName.get(name2), amount2);
		
		ItemStack[] byproducts = new ItemStack[0];
		if(obj.has("byproducts")) byproducts = this.readItemStackArray(obj.get("byproducts").getAsJsonArray());
		
		recipes.put(input, new ElectrolysisMetalRecipe(output1, output2, byproducts));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<AStack, ElectrolysisMetalRecipe> rec = (Entry) recipe;
		
		writer.name("input"); this.writeAStack(rec.getKey(), writer);
		
		writer.name("output1");
		writer.beginArray();
		writer.setIndent("");
		writer.value(rec.getValue().output1.material.names[0]).value(rec.getValue().output1.amount);
		writer.endArray();
		writer.setIndent("  ");
		
		writer.name("output2");
		writer.beginArray();
		writer.setIndent("");
		writer.value(rec.getValue().output2.material.names[0]).value(rec.getValue().output2.amount);
		writer.endArray();
		writer.setIndent("  ");
		
		if(rec.getValue().byproduct != null && rec.getValue().byproduct.length > 0) {
			writer.name("byproducts").beginArray();
			for(ItemStack stack : rec.getValue().byproduct) this.writeItemStack(stack, writer);
			writer.endArray();
		}
	}
	
	public static class ElectrolysisMetalRecipe {
		
		public MaterialStack output1;
		public MaterialStack output2;
		public ItemStack[] byproduct;
		public int duration;
		
		public ElectrolysisMetalRecipe(MaterialStack output1, MaterialStack output2, ItemStack... byproduct) {
			this.output1 = output1;
			this.output2 = output2;
			this.byproduct = byproduct;
			duration = 600;
		}
		public ElectrolysisMetalRecipe(MaterialStack output1, MaterialStack output2, int duration, ItemStack... byproduct) {
			this.output1 = output1;
			this.output2 = output2;
			this.byproduct = byproduct;
			this.duration = duration;
		}
	}
}
