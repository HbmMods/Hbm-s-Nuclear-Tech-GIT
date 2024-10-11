package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.inventory.fluid.Fluids.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.special.ItemBedrockOreNew;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreGrade;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PyroOvenRecipes extends SerializableRecipe {
	
	public static List<PyroOvenRecipe> recipes = new ArrayList();

	@Override
	public void registerDefaults() {
		
		//solid fuel
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
		registerSFAuto(SOURGAS);
		registerSFAuto(REFORMGAS);
		registerSFAuto(SYNGAS);
		registerSFAuto(PETROLEUM);
		registerSFAuto(LPG);
		registerSFAuto(BIOFUEL);
		registerSFAuto(AROMATICS);
		registerSFAuto(UNSATURATEDS);
		registerSFAuto(REFORMATE);
		registerSFAuto(XYLENE);
		registerSFAuto(BALEFIRE, 24_000_000L, ModItems.solid_fuel_bf);
		
		//bedrock ores
		
		for(BedrockOreType type : BedrockOreType.values()) {
			recipes.add(new PyroOvenRecipe(10).in(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.BASE, type))).out(new FluidStack(Fluids.VITRIOL, 50)).out(ItemBedrockOreNew.make(BedrockOreGrade.BASE_ROASTED, type)));
			recipes.add(new PyroOvenRecipe(10).in(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY, type))).out(new FluidStack(Fluids.VITRIOL, 50)).out(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY_ROASTED, type)));
			recipes.add(new PyroOvenRecipe(10).in(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SULFURIC_BYPRODUCT, type))).out(new FluidStack(Fluids.VITRIOL, 50)).out(ItemBedrockOreNew.make(BedrockOreGrade.SULFURIC_ROASTED, type)));
			recipes.add(new PyroOvenRecipe(10).in(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SOLVENT_BYPRODUCT, type))).out(new FluidStack(Fluids.VITRIOL, 50)).out(ItemBedrockOreNew.make(BedrockOreGrade.SOLVENT_ROASTED, type)));
			recipes.add(new PyroOvenRecipe(10).in(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.RAD_BYPRODUCT, type))).out(new FluidStack(Fluids.VITRIOL, 50)).out(ItemBedrockOreNew.make(BedrockOreGrade.RAD_ROASTED, type)));
		}
		
		//syngas from coal
		recipes.add(new PyroOvenRecipe(100)
				.in(new FluidStack(Fluids.STEAM, 500)).in(new OreDictStack(COAL.gem()))
				.out(new FluidStack(Fluids.SYNGAS, 1_000)));
		recipes.add(new PyroOvenRecipe(100)
				.in(new FluidStack(Fluids.STEAM, 500)).in(new OreDictStack(COAL.dust()))
				.out(new FluidStack(Fluids.SYNGAS, 1_000)));
		recipes.add(new PyroOvenRecipe(100)
				.in(new FluidStack(Fluids.STEAM, 250)).in(new OreDictStack(ANY_COKE.gem()))
				.out(new FluidStack(Fluids.SYNGAS, 1_000)));
		//syngas from biomass
		recipes.add(new PyroOvenRecipe(100)
				.in(new ComparableStack(ModItems.biomass, 4))
				.out(new FluidStack(Fluids.SYNGAS, 1_000)).out(new ItemStack(Items.coal, 1, 1)));
		//soot from tar
		recipes.add(new PyroOvenRecipe(40)
				.out(new FluidStack(Fluids.HYDROGEN, 250)).in(new OreDictStack(ANY_TAR.any(), 4))
				.out(new FluidStack(Fluids.CARBONDIOXIDE, 1_000)).out(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.SOOT)));
		//heavyoil from coal
		recipes.add(new PyroOvenRecipe(100)
				.in(new FluidStack(Fluids.HYDROGEN, 500)).in(new OreDictStack(COAL.gem()))
				.out(new FluidStack(Fluids.HEAVYOIL, 1_000)));
		recipes.add(new PyroOvenRecipe(100)
				.in(new FluidStack(Fluids.HYDROGEN, 500)).in(new OreDictStack(COAL.dust()))
				.out(new FluidStack(Fluids.HEAVYOIL, 1_000)));
		recipes.add(new PyroOvenRecipe(100)
				.in(new FluidStack(Fluids.HYDROGEN, 250)).in(new OreDictStack(ANY_COKE.gem()))
				.out(new FluidStack(Fluids.HEAVYOIL, 1_000)));
		//coalgas from coal
		recipes.add(new PyroOvenRecipe(50)
				.in(new FluidStack(Fluids.HEAVYOIL, 500)).in(new OreDictStack(COAL.gem()))
				.out(new FluidStack(Fluids.COALGAS, 1_000)));
		recipes.add(new PyroOvenRecipe(50)
				.in(new FluidStack(Fluids.HEAVYOIL, 500)).in(new OreDictStack(COAL.dust()))
				.out(new FluidStack(Fluids.COALGAS, 1_000)));
		recipes.add(new PyroOvenRecipe(50)
				.in(new FluidStack(Fluids.HEAVYOIL, 500)).in(new OreDictStack(ANY_COKE.gem()))
				.out(new FluidStack(Fluids.COALGAS, 1_000)));
		//refgas from coker gas
		recipes.add(new PyroOvenRecipe(60)
				.in(new FluidStack(GAS_COKER, 4_000))
				.out(new FluidStack(Fluids.REFORMGAS, 100)));
	}

	private static void registerSFAuto(FluidType fluid) {
		registerSFAuto(fluid, 1_440_000L, ModItems.solid_fuel); //3200 burntime * 1.5 burntime bonus * 300 TU/t
	}
	private static void registerSFAuto(FluidType fluid, long tuPerSF, Item fuel) {
		long tuPerBucket = fluid.getTrait(FT_Flammable.class).getHeatEnergy();
		double bonus = 0.5D; //double efficiency!!
		
		int mB = (int) (tuPerSF * 1000L * bonus / tuPerBucket);

		if(mB > 10_000) mB -= (mB % 1000);
		else if(mB > 1_000) mB -= (mB % 100);
		else if(mB > 100) mB -= (mB % 10);
		
		mB = Math.max(mB, 1);

		registerRecipe(fluid, mB, fuel);
	}

	private static void registerRecipe(FluidType type, int quantity, Item output) { registerRecipe(type, quantity, new ItemStack(output)); }
	private static void registerRecipe(FluidType type, int quantity, ItemStack output) { recipes.add(new PyroOvenRecipe(60).in(new FluidStack(type, quantity)).out(output)); }

	public static HashMap getRecipes() {
		HashMap<Object[], Object[]> map = new HashMap<Object[], Object[]>();
		
		for(PyroOvenRecipe rec : recipes) {

			Object[] in = null;
			Object[] out = null;

			if(rec.inputFluid != null && rec.inputItem != null) in = new Object[] {ItemFluidIcon.make(rec.inputFluid), rec.inputItem};
			if(rec.inputFluid != null && rec.inputItem == null) in = new Object[] {ItemFluidIcon.make(rec.inputFluid)};
			if(rec.inputFluid == null && rec.inputItem != null) in = new Object[] {rec.inputItem};

			if(rec.outputFluid != null && rec.outputItem != null) out = new Object[] {rec.outputItem, ItemFluidIcon.make(rec.outputFluid)};
			if(rec.outputFluid != null && rec.outputItem == null) out = new Object[] {ItemFluidIcon.make(rec.outputFluid)};
			if(rec.outputFluid == null && rec.outputItem != null) out = new Object[] {rec.outputItem};
			
			if(in != null && out != null) {
				map.put(in, out);
			}
		}
		
		return map;
	}

	@Override
	public String getFileName() {
		return "hbmPyrolysis.json";
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

		AStack inputItem = obj.has("inputItem") ? this.readAStack(obj.get("inputItem").getAsJsonArray()) : null;
		FluidStack inputFluid = obj.has("inputFluid") ? this.readFluidStack(obj.get("inputFluid").getAsJsonArray()) : null;
		ItemStack outputItem = obj.has("outputItem") ? this.readItemStack(obj.get("outputItem").getAsJsonArray()) : null;
		FluidStack outputFluid = obj.has("outputFluid") ? this.readFluidStack(obj.get("outputFluid").getAsJsonArray()) : null;
		int duration = obj.get("duration").getAsInt();
		
		recipes.add(new PyroOvenRecipe(duration).in(inputFluid).in(inputItem).out(outputFluid).out(outputItem));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		
		PyroOvenRecipe rec = (PyroOvenRecipe) recipe;

		if(rec.inputFluid != null) { writer.name("inputFluid"); this.writeFluidStack(rec.inputFluid, writer); }
		if(rec.inputItem != null) { writer.name("inputItem"); this.writeAStack(rec.inputItem, writer); }
		if(rec.outputFluid != null) { writer.name("outputFluid"); this.writeFluidStack(rec.outputFluid, writer); }
		if(rec.outputItem != null) { writer.name("outputItem"); this.writeItemStack(rec.outputItem, writer); }
		writer.name("duration").value(rec.duration);
	}
	
	public static class PyroOvenRecipe {
		public FluidStack inputFluid;
		public AStack inputItem;
		public FluidStack outputFluid;
		public ItemStack outputItem;
		public int duration;
		
		public PyroOvenRecipe(int duration) {
			this.duration = duration;
		}

		public PyroOvenRecipe in(FluidStack stack) { this.inputFluid = stack; return this; }
		public PyroOvenRecipe in(AStack stack) { this.inputItem = stack; return this; }
		public PyroOvenRecipe out(FluidStack stack) { this.outputFluid = stack; return this; }
		public PyroOvenRecipe out(ItemStack stack) { this.outputItem = stack; return this; }
	}
}
