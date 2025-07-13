package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumBriquetteType;
import com.hbm.items.ItemEnums.EnumCasingType;
import com.hbm.items.ItemEnums.EnumPages;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemStamp;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.machine.ItemStamp.StampType;
import com.hbm.util.Tuple.Pair;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PressRecipes extends SerializableRecipe {

	public static HashMap<Pair<AStack, StampType>, ItemStack> recipes = new HashMap();
	
	public static ItemStack getOutput(ItemStack ingredient, ItemStack stamp) {
		
		if(ingredient == null || stamp == null)
			return null;
		
		if(!(stamp.getItem() instanceof ItemStamp))
			return null;
		
		StampType type = ((ItemStamp) stamp.getItem()).getStampType(stamp.getItem(), stamp.getItemDamage());
		
		for(Entry<Pair<AStack, StampType>, ItemStack> recipe : recipes.entrySet()) {
			
			if(recipe.getKey().getValue() == type && recipe.getKey().getKey().matchesRecipe(ingredient, true))
				return recipe.getValue();
		}
		
		return null;
	}

	@Override
	public void registerDefaults() {

		makeRecipe(StampType.FLAT, new OreDictStack(NETHERQUARTZ.dust()),					Items.quartz);
		makeRecipe(StampType.FLAT, new OreDictStack(LAPIS.dust()),							new ItemStack(Items.dye, 1, 4));
		makeRecipe(StampType.FLAT, new OreDictStack(DIAMOND.dust()),						Items.diamond);
		makeRecipe(StampType.FLAT, new OreDictStack(EMERALD.dust()),						Items.emerald);
		makeRecipe(StampType.FLAT, new ComparableStack(ModItems.biomass),					ModItems.biomass_compressed);
		makeRecipe(StampType.FLAT, new OreDictStack(ANY_COKE.gem()),						ModItems.ingot_graphite);
		makeRecipe(StampType.FLAT, new ComparableStack(ModItems.meteorite_sword_reforged),	ModItems.meteorite_sword_hardened);
		makeRecipe(StampType.FLAT, new ComparableStack(Blocks.log, 1, 3),					ModItems.ball_resin);

		makeRecipe(StampType.FLAT, new OreDictStack(COAL.dust()),							DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.COAL));
		makeRecipe(StampType.FLAT, new OreDictStack(LIGNITE.dust()),						DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.LIGNITE));
		makeRecipe(StampType.FLAT, new ComparableStack(ModItems.powder_sawdust),			DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.WOOD));

		makeRecipe(StampType.PLATE, new OreDictStack(IRON.ingot()),			ModItems.plate_iron);
		makeRecipe(StampType.PLATE, new OreDictStack(GOLD.ingot()),			ModItems.plate_gold);
		makeRecipe(StampType.PLATE, new OreDictStack(TI.ingot()),			ModItems.plate_titanium);
		makeRecipe(StampType.PLATE, new OreDictStack(AL.ingot()),			ModItems.plate_aluminium);
		makeRecipe(StampType.PLATE, new OreDictStack(STEEL.ingot()),		ModItems.plate_steel);
		makeRecipe(StampType.PLATE, new OreDictStack(PB.ingot()),			ModItems.plate_lead);
		makeRecipe(StampType.PLATE, new OreDictStack(CU.ingot()),			ModItems.plate_copper);
		makeRecipe(StampType.PLATE, new OreDictStack(ALLOY.ingot()),		ModItems.plate_advanced_alloy);
		makeRecipe(StampType.PLATE, new OreDictStack(SA326.ingot()),		ModItems.plate_schrabidium);
		makeRecipe(StampType.PLATE, new OreDictStack(CMB.ingot()),			ModItems.plate_combine_steel);
		makeRecipe(StampType.PLATE, new OreDictStack(GUNMETAL.ingot()),		ModItems.plate_gunmetal);
		makeRecipe(StampType.PLATE, new OreDictStack(WEAPONSTEEL.ingot()),	ModItems.plate_weaponsteel);
		makeRecipe(StampType.PLATE, new OreDictStack(BIGMT.ingot()),		ModItems.plate_saturnite);
		makeRecipe(StampType.PLATE, new OreDictStack(DURA.ingot()),			ModItems.plate_dura_steel);

		makeRecipe(StampType.C9, 	new OreDictStack(GUNMETAL.plate()),		DictFrame.fromOne(ModItems.casing, EnumCasingType.SMALL, 4));
		makeRecipe(StampType.C50, 	new OreDictStack(GUNMETAL.plate()),		DictFrame.fromOne(ModItems.casing, EnumCasingType.LARGE, 2));
		makeRecipe(StampType.C9, 	new OreDictStack(WEAPONSTEEL.plate()),	DictFrame.fromOne(ModItems.casing, EnumCasingType.SMALL_STEEL, 4));
		makeRecipe(StampType.C50, 	new OreDictStack(WEAPONSTEEL.plate()),	DictFrame.fromOne(ModItems.casing, EnumCasingType.LARGE_STEEL, 2));

		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.autogen.contains(MaterialShapes.WIRE) && OreDictionary.doesOreNameExist(MaterialShapes.INGOT.make(mat))) {
				makeRecipe(StampType.WIRE, new OreDictStack(MaterialShapes.INGOT.make(mat)), new ItemStack(ModItems.wire_fine, 8, mat.id));
			}
		}

		makeRecipe(StampType.CIRCUIT, new OreDictStack(SI.billet()),						DictFrame.fromOne(ModItems.circuit, EnumCircuitType.SILICON));

		makeRecipe(StampType.PRINTING1, new ComparableStack(Items.paper), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE1));
		makeRecipe(StampType.PRINTING2, new ComparableStack(Items.paper), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE2));
		makeRecipe(StampType.PRINTING3, new ComparableStack(Items.paper), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE3));
		makeRecipe(StampType.PRINTING4, new ComparableStack(Items.paper), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE4));
		makeRecipe(StampType.PRINTING5, new ComparableStack(Items.paper), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE5));
		makeRecipe(StampType.PRINTING6, new ComparableStack(Items.paper), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE6));
		makeRecipe(StampType.PRINTING7, new ComparableStack(Items.paper), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE7));
		makeRecipe(StampType.PRINTING8, new ComparableStack(Items.paper), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE8));
	}

	public static void makeRecipe(StampType type, AStack in, Item out) {
		recipes.put(new Pair<AStack, StampType>(in, type),  new ItemStack(out));
	}
	public static void makeRecipe(StampType type, AStack in, ItemStack out) {
		recipes.put(new Pair<AStack, StampType>(in, type),  out);
	}

	@Override
	public String getFileName() {
		return "hbmPress.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		
		AStack input = this.readAStack(obj.get("input").getAsJsonArray());
		StampType stamp = StampType.valueOf(obj.get("stamp").getAsString().toUpperCase());
		ItemStack output = this.readItemStack(obj.get("output").getAsJsonArray());
		
		if(stamp != null) {
			makeRecipe(stamp, input, output);
		}
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<Pair<AStack, StampType>, ItemStack> entry = (Entry<Pair<AStack, StampType>, ItemStack>) recipe;
		
		writer.name("input");
		this.writeAStack(entry.getKey().getKey(), writer);
		writer.name("stamp").value(entry.getKey().getValue().name().toLowerCase(Locale.US));
		writer.name("output");
		this.writeItemStack(entry.getValue(), writer);
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}
}
