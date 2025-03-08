package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Pair;

import net.minecraft.item.ItemStack;

public class CrackingRecipes extends SerializableRecipe {
	
	//cracking in percent
	public static final int oil_crack_oil = 80;
	public static final int oil_crack_petro = 20;
	public static final int bitumen_crack_oil = 80;
	public static final int bitumen_crack_aroma = 20;
	public static final int smear_crack_napht = 60;
	public static final int smear_crack_petro = 40;
	public static final int gas_crack_petro = 30;
	public static final int gas_crack_unsat = 20;
	public static final int diesel_crack_kero = 40;
	public static final int diesel_crack_petro = 30;
	public static final int kero_crack_petro = 60;
	public static final int wood_crack_aroma = 10;
	public static final int wood_crack_heat = 40;
	public static final int xyl_crack_aroma = 80;
	public static final int xyl_crack_petro = 20;
	
	public static Map<FluidType, Pair<FluidStack, FluidStack>> cracking = new HashMap();
	
	@Override
	public void registerDefaults() {
		cracking.put(Fluids.OIL,				new Pair(new FluidStack(Fluids.CRACKOIL,		oil_crack_oil),		new FluidStack(Fluids.PETROLEUM,	oil_crack_petro)));
		cracking.put(Fluids.BITUMEN,			new Pair(new FluidStack(Fluids.OIL,				bitumen_crack_oil),	new FluidStack(Fluids.AROMATICS,	bitumen_crack_aroma)));
		cracking.put(Fluids.SMEAR,				new Pair(new FluidStack(Fluids.NAPHTHA,			smear_crack_napht),	new FluidStack(Fluids.PETROLEUM,	smear_crack_petro)));
		cracking.put(Fluids.GAS,				new Pair(new FluidStack(Fluids.PETROLEUM,		gas_crack_petro),	new FluidStack(Fluids.UNSATURATEDS,	gas_crack_unsat)));
		cracking.put(Fluids.DIESEL,				new Pair(new FluidStack(Fluids.KEROSENE,		diesel_crack_kero),	new FluidStack(Fluids.PETROLEUM,	diesel_crack_petro)));
		cracking.put(Fluids.DIESEL_CRACK,		new Pair(new FluidStack(Fluids.KEROSENE,		diesel_crack_kero),	new FluidStack(Fluids.PETROLEUM,	diesel_crack_petro)));
		cracking.put(Fluids.KEROSENE,			new Pair(new FluidStack(Fluids.PETROLEUM,		kero_crack_petro),	new FluidStack(Fluids.NONE,			0)));
		cracking.put(Fluids.WOODOIL,			new Pair(new FluidStack(Fluids.HEATINGOIL,		wood_crack_heat),	new FluidStack(Fluids.AROMATICS,	wood_crack_aroma)));
		cracking.put(Fluids.XYLENE,				new Pair(new FluidStack(Fluids.AROMATICS,		xyl_crack_aroma),	new FluidStack(Fluids.PETROLEUM,	xyl_crack_petro)));
		cracking.put(Fluids.HEATINGOIL_VACUUM,	new Pair(new FluidStack(Fluids.HEATINGOIL,		80),				new FluidStack(Fluids.REFORMGAS,	20)));
		cracking.put(Fluids.REFORMATE,			new Pair(new FluidStack(Fluids.UNSATURATEDS,	40),				new FluidStack(Fluids.REFORMGAS,	60)));
	}
	
	public static Pair<FluidStack, FluidStack> getCracking(FluidType oil) {
		return cracking.get(oil);
	}
	
	protected static Map<FluidType, Pair<FluidStack, FluidStack>> getCrackingRecipes() {
		return cracking;
	}
	
	public static HashMap<Object, Object> getCrackingRecipesForNEI() {

		HashMap<Object, Object> recipes = new HashMap();
		
		for(Entry<FluidType, Pair<FluidStack, FluidStack>> recipe : cracking.entrySet()) {
			ItemStack[] in = new ItemStack[] {
					ItemFluidIcon.make(recipe.getKey(), 100),
					ItemFluidIcon.make(Fluids.STEAM, 200)
			};
			ItemStack[] out = new ItemStack[] {
					ItemFluidIcon.make(recipe.getValue().getKey()),
					ItemFluidIcon.make(recipe.getValue().getValue()),
					ItemFluidIcon.make(Fluids.SPENTSTEAM, 2)
			};
			
			recipes.put(in, recipe.getValue().getValue().type == Fluids.NONE ? new ItemStack[] {ItemFluidIcon.make(recipe.getValue().getKey()), ItemFluidIcon.make(Fluids.SPENTSTEAM, 2)} : out);
		}
		
		return recipes;
	}
	
	@Override
	public String getFileName() {
		return "hbmCracking.json";
	}
	
	@Override
	public String getComment() {
		return "Inputs are always 100mB, set output quantities accordingly. The steam in/outputs are fixed, using 200mB of steam per 100mB of input.";
	}
	
	@Override
	public Object getRecipeObject() {
		return cracking;
	}

	@Override
	public void deleteRecipes() {
		cracking.clear();
	}
	
	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		FluidType input = Fluids.fromName(obj.get("input").getAsString());
		FluidStack output1 = this.readFluidStack(obj.get("output1").getAsJsonArray());
		FluidStack output2 = this.readFluidStack(obj.get("output2").getAsJsonArray());
		
		cracking.put(input, new Pair(output1, output2));
	}
	
	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Pair<FluidStack, FluidStack>> rec = (Entry<FluidType, Pair<FluidStack, FluidStack>>) recipe;
		
		writer.name("input").value(rec.getKey().getName());
		writer.name("output1"); this.writeFluidStack(rec.getValue().getKey(), writer);
		writer.name("output2"); this.writeFluidStack(rec.getValue().getValue(), writer);
	}
}
