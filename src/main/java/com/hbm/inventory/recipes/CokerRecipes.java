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
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CokerRecipes extends SerializableRecipe {

	public static HashMap<FluidType, Triplet<Integer, ItemStack, FluidStack>> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		registerAuto(HEAVYOIL,				OIL_COKER);
		registerAuto(HEAVYOIL_VACUUM,		REFORMATE);
		registerAuto(COALCREOSOTE,			NAPHTHA_COKER);
		registerAuto(SMEAR,					OIL_COKER);
		registerAuto(HEATINGOIL,			OIL_COKER);
		registerAuto(HEATINGOIL_VACUUM,		OIL_COKER);
		registerAuto(RECLAIMED,				NAPHTHA_COKER);
		registerAuto(NAPHTHA,				NAPHTHA_COKER);
		registerAuto(NAPHTHA_DS,			NAPHTHA_COKER);
		registerAuto(NAPHTHA_CRACK,			NAPHTHA_COKER);
		registerAuto(DIESEL,				NAPHTHA_COKER);
		registerAuto(DIESEL_REFORM,			NAPHTHA_COKER);
		registerAuto(DIESEL_CRACK,			GAS_COKER);
		registerAuto(DIESEL_CRACK_REFORM,	GAS_COKER);
		registerAuto(LIGHTOIL,				GAS_COKER);
		registerAuto(LIGHTOIL_DS,			GAS_COKER);
		registerAuto(LIGHTOIL_CRACK,		GAS_COKER);
		registerAuto(LIGHTOIL_VACUUM,		GAS_COKER);
		registerAuto(BIOFUEL,				GAS_COKER);
		registerAuto(AROMATICS,				GAS_COKER);
		registerAuto(REFORMATE,				GAS_COKER);
		registerAuto(XYLENE,				GAS_COKER);
		registerAuto(FISHOIL,				MERCURY);
		registerAuto(SUNFLOWEROIL,			GAS_COKER);

		registerSFAuto(WOODOIL, 340_000L, new ItemStack(Items.coal, 1, 1), GAS_COKER);

		registerRecipe(WATZ, 4_000, new ItemStack(ModItems.ingot_mud, 4), null);
		registerRecipe(REDMUD, 450, new ItemStack(Items.iron_ingot, 1), new FluidStack(MERCURY, 50));
		registerRecipe(BITUMEN, 16_000, DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), new FluidStack(OIL_COKER, 1_600));
		registerRecipe(LUBRICANT, 12_000, DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), new FluidStack(OIL_COKER, 1_200));
		registerRecipe(CALCIUM_SOLUTION, 125, new ItemStack(ModItems.powder_calcium), new FluidStack(SPENTSTEAM, 100));
		//only cokable gas to extract sulfur content
		registerRecipe(SOURGAS, 1_000, new ItemStack(ModItems.sulfur), new FluidStack(GAS_COKER, 150));
		registerRecipe(SLOP, 1000, new ItemStack(ModItems.powder_limestone), new FluidStack(COLLOID, 250));
		registerRecipe(VITRIOL, 4000, new ItemStack(ModItems.powder_iron), new FluidStack(SULFURIC_ACID, 500));
	}

	public static void registerAuto(FluidType fluid, FluidType type) {
		registerSFAuto(fluid, 820_000L, DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), type); //3200 burntime * 1.25 burntime bonus * 200 TU/t + 20000TU per operation
	}
	private static void registerSFAuto(FluidType fluid, long tuPerSF, ItemStack fuel, FluidType type) {
		long tuFlammable = fluid.hasTrait(FT_Flammable.class) ? fluid.getTrait(FT_Flammable.class).getHeatEnergy() : 0;
		long tuCombustible = fluid.hasTrait(FT_Combustible.class) ? fluid.getTrait(FT_Combustible.class).getCombustionEnergy() : 0;

		long tuPerBucket = Math.max(tuFlammable, tuCombustible);

		double penalty = 1;//1.1D; //no penalty

		int mB = (int) (tuPerSF * 1000L * penalty / tuPerBucket);

		if(mB > 10_000) mB -= (mB % 1000);
		else if(mB > 1_000) mB -= (mB % 100);
		else if(mB > 100) mB -= (mB % 10);

		FluidStack byproduct = type == null ? null : new FluidStack(type, Math.max(10, mB / 10));

		registerRecipe(fluid, mB, fuel, byproduct);
	}
	private static void registerRecipe(FluidType type, int quantity, ItemStack output, FluidStack byproduct) {
		recipes.put(type, new Triplet(quantity, output, byproduct));
	}

	public static Triplet<Integer, ItemStack, FluidStack> getOutput(FluidType type) {
		return recipes.get(type);
	}

	public static HashMap<ItemStack, ItemStack[]> getRecipes() {

		HashMap<ItemStack, ItemStack[]> recipes = new HashMap<ItemStack, ItemStack[]>();

		for(Entry<FluidType, Triplet<Integer, ItemStack, FluidStack>> entry : CokerRecipes.recipes.entrySet()) {

			FluidType type = entry.getKey();
			int amount = entry.getValue().getX();
			ItemStack out = entry.getValue().getY().copy();
			FluidStack byproduct = entry.getValue().getZ();


			if(out != null && byproduct != null) recipes.put(ItemFluidIcon.make(type, amount), new ItemStack[] {out, ItemFluidIcon.make(byproduct)});
			if(out != null && byproduct == null) recipes.put(ItemFluidIcon.make(type, amount), new ItemStack[] {out});
			if(out == null && byproduct != null) recipes.put(ItemFluidIcon.make(type, amount), new ItemStack[] {ItemFluidIcon.make(byproduct)});
		}

		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmCoker.json";
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
		ItemStack out = obj.has("output") ? this.readItemStack(obj.get("output").getAsJsonArray()) : null;
		FluidStack byproduct = obj.has("byproduct") ? this.readFluidStack(obj.get("byproduct").getAsJsonArray()) : null;
		recipes.put(in.type, new Triplet(in.fill, out, byproduct));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Triplet<Integer, ItemStack, FluidStack>> rec = (Entry<FluidType, Triplet<Integer, ItemStack, FluidStack>>) recipe;
		FluidStack in = new FluidStack(rec.getKey(), rec.getValue().getX());
		writer.name("input");
		this.writeFluidStack(in, writer);
		if(rec.getValue().getY() != null) {
			writer.name("output");
			this.writeItemStack(rec.getValue().getY(), writer);
		}
		if(rec.getValue().getZ() != null) {
			writer.name("byproduct");
			this.writeFluidStack(rec.getValue().getZ(), writer);
		}
	}
}
