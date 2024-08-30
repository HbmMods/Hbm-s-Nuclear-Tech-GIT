package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.OreNames;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumBriquetteType;
import com.hbm.items.ItemEnums.EnumPages;
import com.hbm.items.ItemAmmoEnums.Ammo357Magnum;
import com.hbm.items.ItemAmmoEnums.Ammo556mm;
import com.hbm.items.ItemAmmoEnums.AmmoLunaticSniper;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemStamp;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.machine.ItemStamp.StampType;
import com.hbm.util.Tuple.Pair;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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

		makeRecipe(StampType.PLATE, new OreDictStack(IRON.ingot()),		ModItems.plate_iron);
		makeRecipe(StampType.PLATE, new OreDictStack(GOLD.ingot()),		ModItems.plate_gold);
		makeRecipe(StampType.PLATE, new OreDictStack(TI.ingot()),		ModItems.plate_titanium);
		makeRecipe(StampType.PLATE, new OreDictStack(AL.ingot()),		ModItems.plate_aluminium);
		makeRecipe(StampType.PLATE, new OreDictStack(STEEL.ingot()),	ModItems.plate_steel);
		makeRecipe(StampType.PLATE, new OreDictStack(PB.ingot()),		ModItems.plate_lead);
		makeRecipe(StampType.PLATE, new OreDictStack(CU.ingot()),		ModItems.plate_copper);
		makeRecipe(StampType.PLATE, new OreDictStack(ALLOY.ingot()),	ModItems.plate_advanced_alloy);
		makeRecipe(StampType.PLATE, new OreDictStack(SA326.ingot()),	ModItems.plate_schrabidium);
		makeRecipe(StampType.PLATE, new OreDictStack(CMB.ingot()),		ModItems.plate_combine_steel);
		makeRecipe(StampType.PLATE, new OreDictStack(BIGMT.ingot()),	ModItems.plate_saturnite);
		makeRecipe(StampType.PLATE, new OreDictStack(DURA.ingot()),		ModItems.plate_dura_steel);

		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.shapes.contains(MaterialShapes.WIRE) && mat.shapes.contains(MaterialShapes.INGOT)) {
				makeRecipe(StampType.WIRE, new OreDictStack(OreNames.INGOT + mat.names[0]), new ItemStack(ModItems.wire_fine, 8, mat.id));
			}
		}

		makeRecipe(StampType.CIRCUIT, new OreDictStack(SI.billet()),						DictFrame.fromOne(ModItems.circuit, EnumCircuitType.SILICON));

		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_iron),			ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.IRON));
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_steel),		ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.LEAD));
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_lead),			ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.NUCLEAR));
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_gold),			ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.GOLD));
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_schrabidium),	ModItems.ammo_357.stackFromEnum(6, Ammo357Magnum.SCHRABIDIUM));
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_nightmare),	ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.NIGHTMARE1));
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_desh),			ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.DESH));
		makeRecipe(StampType.C357, new OreDictStack(STEEL.ingot()),						ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.STEEL));
		
		makeRecipe(StampType.C44, new ComparableStack(ModItems.assembly_nopip),		new ItemStack(ModItems.ammo_44, 24));
		makeRecipe(StampType.C44, new ComparableStack(ModItems.assembly_45), 		new ItemStack(ModItems.ammo_45, 32));

		makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_smg),		new ItemStack(ModItems.ammo_9mm, 32));
		makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_uzi),		new ItemStack(ModItems.ammo_22lr, 32));
		makeRecipe(StampType.C9, new OreDictStack(GOLD.ingot()),					ModItems.ammo_556.stackFromEnum(32, Ammo556mm.GOLD));
		makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_lacunae),	new ItemStack(ModItems.ammo_5mm, 64));
		makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_556),		new ItemStack(ModItems.ammo_556, 32));

		makeRecipe(StampType.C50, new ComparableStack(ModItems.assembly_calamity),		new ItemStack(ModItems.ammo_50bmg, 12));
		makeRecipe(StampType.C50, new ComparableStack(ModItems.assembly_actionexpress),	new ItemStack(ModItems.ammo_50ae, 12));
		makeRecipe(StampType.C50, new ComparableStack(ModItems.assembly_luna), 			ModItems.ammo_luna_sniper.stackFromEnum(4, AmmoLunaticSniper.SABOT));
		makeRecipe(StampType.C50, new ComparableStack(ModItems.assembly_762), 			new ItemStack(ModItems.ammo_762, 32));

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
