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
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.item.ItemStack;

public class CokerRecipes extends SerializableRecipe {
	
	private static HashMap<FluidType, Triplet<Integer, ItemStack, FluidStack>> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		registerAuto(HEAVYOIL,				new FluidStack(Fluids.OIL_COKER,		20));
		registerAuto(HEAVYOIL_VACUUM,		new FluidStack(Fluids.REFORMATE,		20));
		registerAuto(COALCREOSOTE,			new FluidStack(Fluids.NAPHTHA_COKER,	20));
		registerAuto(WOODOIL,				new FluidStack(Fluids.NAPHTHA_COKER,	20));
		registerAuto(SMEAR,					new FluidStack(Fluids.OIL_COKER,		20));
		registerAuto(HEATINGOIL,			new FluidStack(Fluids.OIL_COKER,		20));
		registerAuto(HEATINGOIL_VACUUM,		new FluidStack(Fluids.OIL_COKER,		20));
		registerAuto(RECLAIMED,				new FluidStack(Fluids.NAPHTHA_COKER,	20));
		registerAuto(NAPHTHA,				new FluidStack(Fluids.NAPHTHA_COKER,	20));
		registerAuto(NAPHTHA_CRACK,			new FluidStack(Fluids.NAPHTHA_COKER,	20));
		registerAuto(DIESEL,				new FluidStack(Fluids.NAPHTHA_COKER,	20));
		registerAuto(DIESEL_REFORM,			new FluidStack(Fluids.NAPHTHA_COKER,	20));
		registerAuto(DIESEL_CRACK,			new FluidStack(Fluids.GAS_COKER,		20));
		registerAuto(DIESEL_CRACK_REFORM,	new FluidStack(Fluids.GAS_COKER,		20));
		registerAuto(LIGHTOIL,				new FluidStack(Fluids.GAS_COKER,		20));
		registerAuto(LIGHTOIL_CRACK,		new FluidStack(Fluids.GAS_COKER,		20));
		registerAuto(LIGHTOIL_VACUUM,		new FluidStack(Fluids.GAS_COKER,		20));
		registerAuto(BIOFUEL,				new FluidStack(Fluids.GAS_COKER,		20));
		registerAuto(AROMATICS,				new FluidStack(Fluids.GAS_COKER,		20));
		registerAuto(REFORMATE,				new FluidStack(Fluids.GAS_COKER,		20));
		registerAuto(XYLENE,				new FluidStack(Fluids.GAS_COKER,		20));
	}

	private static void registerAuto(FluidType fluid, FluidStack byproduct) {
		registerSFAuto(fluid, 800_000L, DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), byproduct); //3200 burntime * 1.25 burntime bonus * 200 TU/t
	}
	private static void registerSFAuto(FluidType fluid, long tuPerSF, ItemStack fuel, FluidStack byproduct) {
		long tuFlammable = fluid.hasTrait(FT_Flammable.class) ? fluid.getTrait(FT_Flammable.class).getHeatEnergy() : 0;
		long tuCombustible = fluid.hasTrait(FT_Combustible.class) ? fluid.getTrait(FT_Combustible.class).getCombustionEnergy() : 0;
		
		long tuPerBucket = Math.max(tuFlammable, tuCombustible);
		
		double penalty = 1.1D;
		
		int mB = (int) (tuPerSF * 1000L * penalty / tuPerBucket);

		if(mB > 10_000) mB -= (mB % 1000);
		else if(mB > 1_000) mB -= (mB % 100);
		else if(mB > 100) mB -= (mB % 10);

		registerRecipe(fluid, mB, fuel, byproduct);
	}
	private static void registerRecipe(FluidType type, int quantity, ItemStack output, FluidStack byproduct) {
		recipes.put(type, new Triplet(quantity, output, byproduct));
	}

	public static HashMap<ItemStack, ItemStack[]> getRecipes() {
		
		HashMap<ItemStack, ItemStack[]> recipes = new HashMap<ItemStack, ItemStack[]>();
		
		for(Entry<FluidType, Triplet<Integer, ItemStack, FluidStack>> entry : CokerRecipes.recipes.entrySet()) {
			
			FluidType type = entry.getKey();
			int amount = entry.getValue().getX();
			ItemStack out = entry.getValue().getY().copy();
			FluidStack byproduct = entry.getValue().getZ();
			
			recipes.put(ItemFluidIcon.make(type, amount), new ItemStack[] {out, ItemFluidIcon.make(byproduct)});
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
		ItemStack out = this.readItemStack(obj.get("output").getAsJsonArray());
		FluidStack byproduct = this.readFluidStack(obj.get("byproduct").getAsJsonArray());
		recipes.put(in.type, new Triplet(in.fill, out, byproduct));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Triplet<Integer, ItemStack, FluidStack>> rec = (Entry<FluidType, Triplet<Integer, ItemStack, FluidStack>>) recipe;
		FluidStack in = new FluidStack(rec.getKey(), rec.getValue().getX());
		writer.name("input");
		this.writeFluidStack(in, writer);
		writer.name("output");
		this.writeItemStack(rec.getValue().getY(), writer);
		writer.name("byproduct");
		this.writeFluidStack(rec.getValue().getZ(), writer);
	}
}
