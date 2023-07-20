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
import com.hbm.util.Tuple.Quartet;

import net.minecraft.item.ItemStack;

public class CryoRecipes extends SerializableRecipe {
	
	private static HashMap<FluidType, Quartet<FluidStack, FluidStack, FluidStack, FluidStack>> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		recipes.put(Fluids.AIR, new Quartet(
				new FluidStack(Fluids.NITROGEN, 50),
				new FluidStack(Fluids.OXYGEN, 15),
				new FluidStack(Fluids.KRYPTON, 10),
				new FluidStack(Fluids.CARBONDIOXIDE, 10)
				));
		
	} // this is such a sexy machine might use your code for atmospheric distillator
	
	public static Quartet<FluidStack, FluidStack, FluidStack, FluidStack> getOutput(FluidType type) {
		return recipes.get(type);
	}
	
	public static HashMap<Object, Object[]> getRecipes() {

		HashMap<Object, Object[]> map = new HashMap<Object, Object[]>();
		
		for(Entry<FluidType, Quartet<FluidStack, FluidStack, FluidStack, FluidStack>> recipe : recipes.entrySet()) {
			map.put(ItemFluidIcon.make(recipe.getKey(), 1000),
					new ItemStack[] {
							ItemFluidIcon.make(recipe.getValue().getX().type,	recipe.getValue().getX().fill * 10),
							ItemFluidIcon.make(recipe.getValue().getY().type,	recipe.getValue().getY().fill * 10),
							ItemFluidIcon.make(recipe.getValue().getZ().type,	recipe.getValue().getZ().fill * 10),
							ItemFluidIcon.make(recipe.getValue().getW().type,	recipe.getValue().getZ().fill * 10) });

		}
		
		return map;
	}

	@Override
	public String getFileName() {
		return "cryodistillator.json";
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
		FluidStack output4 = this.readFluidStack(obj.get("output4").getAsJsonArray());

		
		recipes.put(input, new Quartet(output1, output2, output3, output4));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Quartet<FluidStack, FluidStack, FluidStack, FluidStack>> rec = (Entry<FluidType, Quartet<FluidStack, FluidStack, FluidStack, FluidStack>>) recipe;
		
		writer.name("input").value(rec.getKey().getName());
		writer.name("output1"); this.writeFluidStack(rec.getValue().getX(), writer);
		writer.name("output2"); this.writeFluidStack(rec.getValue().getY(), writer);
		writer.name("output3"); this.writeFluidStack(rec.getValue().getZ(), writer);
		writer.name("output4"); this.writeFluidStack(rec.getValue().getW(), writer);

	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}
}
