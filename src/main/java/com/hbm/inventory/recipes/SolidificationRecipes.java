package com.hbm.inventory.recipes;

import static com.hbm.inventory.fluid.Fluids.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.ModItems;
import com.hbm.util.Tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SolidificationRecipes extends SerializableRecipe {

	public static final int SF_OIL =		200;
	public static final int SF_CRACK =		200;
	public static final int SF_HEAVY =		150;
	public static final int SF_BITUMEN =	100;
	public static final int SF_SMEAR =		100;
	public static final int SF_HEATING =	50;
	public static final int SF_RECLAIMED =	100;
	public static final int SF_PETROIL =	125;
	public static final int SF_LUBE =		100;
	public static final int SF_NAPH =		150;
	public static final int SF_DIESEL =		200;
	public static final int SF_LIGHT =		225;
	public static final int SF_KEROSENE =	275;
	public static final int SF_GAS =		375;
	public static final int SF_PETROLEUM =	300;
	public static final int SF_LPG =		150;
	public static final int SF_BIOGAS =		1750;
	public static final int SF_BIOFUEL =	750;
	public static final int SF_COALOIL =	200;
	public static final int SF_CREOSOTE =	200;
	public static final int SF_WOOD =		1000;
	//mostly for alternate chemistry, dump into SF if not desired
	public static final int SF_AROMA =		1000;
	public static final int SF_UNSAT =		1000;
	//in the event that these compounds are STILL too useless, add unsat + gas -> kerosene recipe for all those missile junkies
	//aromatics can be idfk wax or soap or sth, perhaps artificial lubricant?
	//on that note, add more leaded variants

	private static HashMap<FluidType, Pair<Integer, ItemStack>> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		registerRecipe(WATER,		1000,			Blocks.ice);
		registerRecipe(LAVA,		1000,			Blocks.obsidian);
		registerRecipe(MERCURY,		125,			ModItems.ingot_mercury);
		registerRecipe(BIOGAS,		250,			ModItems.biomass_compressed);
		registerRecipe(SALIENT,		1280,			new ItemStack(ModItems.bio_wafer, 8)); //4 (food val) * 2 (sat mod) * 2 (constant) * 10 (quanta) * 8 (batch size)
		registerRecipe(ENDERJUICE,	100,			Items.ender_pearl);
		registerRecipe(WATZ,		1000,			ModItems.ingot_mud);
		registerRecipe(REDMUD,		450,			Items.iron_ingot);
		registerRecipe(SODIUM,		100,			ModItems.powder_sodium);
		registerRecipe(LEAD,		100,			ModItems.ingot_lead);
		registerRecipe(SLOP,		250,			ModBlocks.ore_oil_sand);

		registerRecipe(OIL,				SF_OIL,			DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		registerRecipe(CRACKOIL,		SF_CRACK,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK));
		registerRecipe(COALOIL,			SF_COALOIL,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL));
		registerRecipe(HEAVYOIL,		SF_HEAVY,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		registerRecipe(HEAVYOIL_VACUUM,	SF_HEAVY,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		registerRecipe(BITUMEN,			SF_BITUMEN,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		registerRecipe(COALCREOSOTE,	SF_CREOSOTE,	DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL));
		registerRecipe(WOODOIL,			SF_WOOD,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WOOD));
		registerRecipe(LUBRICANT,		SF_LUBE,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN));

		registerRecipe(BALEFIRE,		250,			ModItems.solid_fuel_bf);

		registerSFAuto(SMEAR);
		registerSFAuto(HEATINGOIL);
		registerSFAuto(HEATINGOIL_VACUUM);
		registerSFAuto(RECLAIMED);
		registerSFAuto(PETROIL);
		registerSFAuto(NAPHTHA);
		registerSFAuto(NAPHTHA_CRACK);
		registerSFAuto(DIESEL);
		registerSFAuto(DIESEL_REFORM);
		registerSFAuto(DIESEL_CRACK);
		registerSFAuto(DIESEL_CRACK_REFORM);
		registerSFAuto(LIGHTOIL);
		registerSFAuto(LIGHTOIL_CRACK);
		registerSFAuto(LIGHTOIL_VACUUM);
		registerSFAuto(KEROSENE);
		registerSFAuto(KEROSENE_REFORM);
		//registerSFAuto(GAS);
		registerSFAuto(SOURGAS);
		registerSFAuto(REFORMGAS);
		registerSFAuto(SYNGAS);
		registerSFAuto(PETROLEUM);
		registerSFAuto(LPG);
		//registerSFAuto(BIOGAS);
		registerSFAuto(BIOFUEL);
		registerSFAuto(AROMATICS);
		registerSFAuto(UNSATURATEDS);
		registerSFAuto(REFORMATE);
		registerSFAuto(XYLENE);
		registerSFAuto(BALEFIRE, 24_000_000L, ModItems.solid_fuel_bf); //holy shit this is energy dense*/

	}

	private static void registerSFAuto(FluidType fluid) {
		registerSFAuto(fluid, 1_440_000L, ModItems.solid_fuel); //3200 burntime * 1.5 burntime bonus * 300 TU/t
	}
	private static void registerSFAuto(FluidType fluid, long tuPerSF, Item fuel) {
		long tuPerBucket = fluid.getTrait(FT_Flammable.class).getHeatEnergy();
		double penalty = 1.25D;

		int mB = (int) (tuPerSF * 1000L * penalty / tuPerBucket);

		if(mB > 10_000) mB -= (mB % 1000);
		else if(mB > 1_000) mB -= (mB % 100);
		else if(mB > 100) mB -= (mB % 10);

		mB = Math.max(mB, 1);

		registerRecipe(fluid, mB, fuel);
	}

	private static void registerRecipe(FluidType type, int quantity, Item output) { registerRecipe(type, quantity, new ItemStack(output)); }
	private static void registerRecipe(FluidType type, int quantity, Block output) { registerRecipe(type, quantity, new ItemStack(output)); }
	private static void registerRecipe(FluidType type, int quantity, ItemStack output) {
		recipes.put(type, new Pair<Integer, ItemStack>(quantity, output));
	}

	public static Pair<Integer, ItemStack> getOutput(FluidType type) {
		return recipes.get(type);
	}

	public static HashMap<ItemStack, ItemStack> getRecipes() {

		HashMap<ItemStack, ItemStack> recipes = new HashMap<ItemStack, ItemStack>();

		for(Entry<FluidType, Pair<Integer, ItemStack>> entry : SolidificationRecipes.recipes.entrySet()) {

			FluidType type = entry.getKey();
			int amount = entry.getValue().getKey();
			ItemStack out = entry.getValue().getValue().copy();

			recipes.put(ItemFluidIcon.make(type, amount), out);
		}

		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmSolidifier.json";
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
		FluidStack in = this.readFluidStack(obj.get("input").getAsJsonArray());
		ItemStack out = this.readItemStack(obj.get("output").getAsJsonArray());
		recipes.put(in.type, new Pair(in.fill, out));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Pair<Integer, ItemStack>> rec = (Entry<FluidType, Pair<Integer, ItemStack>>) recipe;
		FluidStack in = new FluidStack(rec.getKey(), rec.getValue().getKey());
		writer.name("input");
		this.writeFluidStack(in, writer);
		writer.name("output");
		this.writeItemStack(rec.getValue().getValue(), writer);
	}
}
