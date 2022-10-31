package com.hbm.inventory.recipes;

import static com.hbm.inventory.fluid.Fluids.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.FluidType;
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
	public static final int SF_LUBE =		125;
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

		registerRecipe(OIL,			SF_OIL,			DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		registerRecipe(CRACKOIL,	SF_OIL,			DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK));
		registerRecipe(COALOIL,		SF_OIL,			DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL));
		registerRecipe(HEAVYOIL,	SF_HEAVY,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		registerRecipe(BITUMEN,		SF_BITUMEN,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));

		registerRecipe(SMEAR,			SF_SMEAR,		ModItems.solid_fuel);
		registerRecipe(HEATINGOIL,		SF_HEATING,		ModItems.solid_fuel);
		registerRecipe(RECLAIMED,		SF_RECLAIMED,	ModItems.solid_fuel);
		registerRecipe(PETROIL,			SF_PETROIL,		ModItems.solid_fuel);
		registerRecipe(LUBRICANT,		SF_LUBE,		ModItems.solid_fuel);
		registerRecipe(NAPHTHA,			SF_NAPH,		ModItems.solid_fuel);
		registerRecipe(NAPHTHA_CRACK,	SF_NAPH,		ModItems.solid_fuel);
		registerRecipe(DIESEL,			SF_DIESEL,		ModItems.solid_fuel);
		registerRecipe(DIESEL_CRACK,	SF_DIESEL,		ModItems.solid_fuel);
		registerRecipe(LIGHTOIL,		SF_LIGHT,		ModItems.solid_fuel);
		registerRecipe(LIGHTOIL_CRACK,	SF_LIGHT,		ModItems.solid_fuel);
		registerRecipe(KEROSENE,		SF_KEROSENE,	ModItems.solid_fuel);
		registerRecipe(GAS,				SF_GAS,			ModItems.solid_fuel);
		registerRecipe(PETROLEUM,		SF_PETROLEUM,	ModItems.solid_fuel);
		registerRecipe(LPG,				SF_LPG,			ModItems.solid_fuel);
		registerRecipe(BIOGAS,			SF_BIOGAS,		ModItems.solid_fuel);
		registerRecipe(BIOFUEL,			SF_BIOFUEL,		ModItems.solid_fuel);
		registerRecipe(AROMATICS,		SF_AROMA,		ModItems.solid_fuel);
		registerRecipe(UNSATURATEDS,	SF_UNSAT,		ModItems.solid_fuel);
		registerRecipe(BALEFIRE,		250,			ModItems.solid_fuel_bf);
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
