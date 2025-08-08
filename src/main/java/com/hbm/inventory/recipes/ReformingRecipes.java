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

public class ReformingRecipes extends SerializableRecipe {
	
	public static HashMap<FluidType, Triplet<FluidStack, FluidStack, FluidStack>> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		recipes.put(Fluids.HEATINGOIL, new Triplet(
				new FluidStack(Fluids.NAPHTHA, 50),
				new FluidStack(Fluids.PETROLEUM, 15),
				new FluidStack(Fluids.HYDROGEN, 10)
				));
		recipes.put(Fluids.NAPHTHA, new Triplet(
				new FluidStack(Fluids.REFORMATE, 50),
				new FluidStack(Fluids.PETROLEUM, 15),
				new FluidStack(Fluids.HYDROGEN, 10)
				));
		recipes.put(Fluids.NAPHTHA_CRACK, new Triplet(
				new FluidStack(Fluids.REFORMATE, 50),
				new FluidStack(Fluids.AROMATICS, 10),
				new FluidStack(Fluids.HYDROGEN, 5)
				));
		recipes.put(Fluids.NAPHTHA_COKER, new Triplet(
				new FluidStack(Fluids.REFORMATE, 50),
				new FluidStack(Fluids.REFORMGAS, 10),
				new FluidStack(Fluids.HYDROGEN, 5)
				));
		recipes.put(Fluids.LIGHTOIL, new Triplet(
				new FluidStack(Fluids.AROMATICS, 50),
				new FluidStack(Fluids.REFORMGAS, 10),
				new FluidStack(Fluids.HYDROGEN, 15)
				));
		recipes.put(Fluids.LIGHTOIL_CRACK, new Triplet(
				new FluidStack(Fluids.AROMATICS, 50),
				new FluidStack(Fluids.REFORMGAS, 5),
				new FluidStack(Fluids.HYDROGEN, 20)
				));
		recipes.put(Fluids.PETROLEUM, new Triplet(
				new FluidStack(Fluids.UNSATURATEDS, 85),
				new FluidStack(Fluids.REFORMGAS, 10),
				new FluidStack(Fluids.HYDROGEN, 5)
				));
		recipes.put(Fluids.SOURGAS, new Triplet(
				new FluidStack(Fluids.SULFURIC_ACID, 75),
				new FluidStack(Fluids.PETROLEUM, 10),
				new FluidStack(Fluids.HYDROGEN, 15)
				));
		recipes.put(Fluids.CHOLESTEROL, new Triplet(
				new FluidStack(Fluids.ESTRADIOL, 50),
				new FluidStack(Fluids.REFORMGAS, 35),
				new FluidStack(Fluids.HYDROGEN, 15)
				));
	}
	
	public static Triplet<FluidStack, FluidStack, FluidStack> getOutput(FluidType type) {
		return recipes.get(type);
	}
	
	public static HashMap<Object, Object[]> getRecipes() {

		HashMap<Object, Object[]> map = new HashMap<Object, Object[]>();
		
		for(Entry<FluidType, Triplet<FluidStack, FluidStack, FluidStack>> recipe : recipes.entrySet()) {
			map.put(ItemFluidIcon.make(recipe.getKey(), 1000),
					new ItemStack[] {
							ItemFluidIcon.make(recipe.getValue().getX().type,	recipe.getValue().getX().fill * 10),
							ItemFluidIcon.make(recipe.getValue().getY().type,	recipe.getValue().getY().fill * 10),
							ItemFluidIcon.make(recipe.getValue().getZ().type,	recipe.getValue().getZ().fill * 10) });
		}
		
		return map;
	}

	@Override
	public String getFileName() {
		return "hbmReforming.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		FluidType input = Fluids.fromName(obj.get("input").getAsString());
		FluidStack output1 = this.readFluidStack(obj.get("output1").getAsJsonArray());
		FluidStack output2 = this.readFluidStack(obj.get("output2").getAsJsonArray());
		FluidStack output3 = this.readFluidStack(obj.get("output3").getAsJsonArray());
		
		recipes.put(input, new Triplet(output1, output2, output3));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Triplet<FluidStack, FluidStack, FluidStack>> rec = (Entry<FluidType, Triplet<FluidStack, FluidStack, FluidStack>>) recipe;
		
		writer.name("input").value(rec.getKey().getName());
		writer.name("output1"); this.writeFluidStack(rec.getValue().getX(), writer);
		writer.name("output2"); this.writeFluidStack(rec.getValue().getY(), writer);
		writer.name("output3"); this.writeFluidStack(rec.getValue().getZ(), writer);
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}
}
