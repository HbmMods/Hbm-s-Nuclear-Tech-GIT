package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.item.ItemStack;

public class HydrotreatingRecipes extends SerializableRecipe {
	
	public static HashMap<FluidType, Triplet<FluidStack, FluidStack, FluidStack>> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		
		recipes.put(Fluids.OIL, new Triplet(
			new FluidStack(Fluids.HYDROGEN, 5, 1),
			new FluidStack(Fluids.OIL_DS, 90),
			new FluidStack(Fluids.SOURGAS, 15)
		));
		
		recipes.put(Fluids.CRACKOIL, new Triplet(
			new FluidStack(Fluids.HYDROGEN, 5, 1),
			new FluidStack(Fluids.CRACKOIL_DS, 90),
			new FluidStack(Fluids.SOURGAS, 15)
		));
		
		recipes.put(Fluids.GAS, new Triplet(
			new FluidStack(Fluids.HYDROGEN, 5, 1),
			new FluidStack(Fluids.PETROLEUM, 80),
			new FluidStack(Fluids.SOURGAS, 15)
		));
		
		recipes.put(Fluids.DIESEL_CRACK, new Triplet(
			new FluidStack(Fluids.HYDROGEN, 10, 1),
			new FluidStack(Fluids.DIESEL, 80),
			new FluidStack(Fluids.SOURGAS, 30)
		));
		
		recipes.put(Fluids.DIESEL_CRACK_REFORM, new Triplet(
			new FluidStack(Fluids.HYDROGEN, 10, 1),
			new FluidStack(Fluids.DIESEL_REFORM, 80),
			new FluidStack(Fluids.SOURGAS, 30)
		));
		
		recipes.put(Fluids.COALOIL, new Triplet(
			new FluidStack(Fluids.HYDROGEN, 10, 1),
			new FluidStack(Fluids.COALGAS, 80),
			new FluidStack(Fluids.SOURGAS, 15)
		));
	}
	
	public static Triplet<FluidStack, FluidStack, FluidStack> getOutput(FluidType type) {
		return recipes.get(type);
	}
	
	public static HashMap<Object, Object[]> getRecipes() {

		HashMap<Object, Object[]> map = new HashMap<Object, Object[]>();
		
		for(Entry<FluidType, Triplet<FluidStack, FluidStack, FluidStack>> recipe : recipes.entrySet()) {
			map.put(new ItemStack[] {
							ItemFluidIcon.make(recipe.getKey(), 1000),
							ItemFluidIcon.make(recipe.getValue().getX().type,	recipe.getValue().getX().fill * 10, 1) },
					new ItemStack[] {
							ItemFluidIcon.make(recipe.getValue().getY().type,	recipe.getValue().getY().fill * 10),
							ItemFluidIcon.make(recipe.getValue().getZ().type,	recipe.getValue().getZ().fill * 10) });
		}
		
		return map;
	}

	@Override
	public String getFileName() {
		return "hbmHydrotreating.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		FluidType input = Fluids.fromName(obj.get("input").getAsString());
		FluidStack hydrogen = this.readFluidStack(obj.get("hydrogen").getAsJsonArray());
		FluidStack output1 = this.readFluidStack(obj.get("output1").getAsJsonArray());
		FluidStack output2 = this.readFluidStack(obj.get("output2").getAsJsonArray());
		
		recipes.put(input, new Triplet(hydrogen, output1, output2));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Triplet<FluidStack, FluidStack, FluidStack>> rec = (Entry<FluidType, Triplet<FluidStack, FluidStack, FluidStack>>) recipe;
		
		writer.name("input").value(rec.getKey().getName());
		writer.name("hydrogen"); this.writeFluidStack(rec.getValue().getX(), writer);
		writer.name("output1"); this.writeFluidStack(rec.getValue().getY(), writer);
		writer.name("output2"); this.writeFluidStack(rec.getValue().getZ(), writer);
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}
}
