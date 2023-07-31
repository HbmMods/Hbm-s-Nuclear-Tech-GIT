package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class ElectrolyserFluidRecipes extends SerializableRecipe {
	
	public static HashMap<FluidType, ElectrolysisRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		recipes.put(Fluids.WATER, new ElectrolysisRecipe(1_000, new FluidStack(Fluids.HYDROGEN, 100), new FluidStack(Fluids.OXYGEN, 100)));
		recipes.put(Fluids.HEAVYWATER, new ElectrolysisRecipe(1_000, new FluidStack(Fluids.DEUTERIUM, 50), new FluidStack(Fluids.OXYGEN, 50)));

		recipes.put(Fluids.POTASSIUM_CHLORIDE, new ElectrolysisRecipe(250, new FluidStack(Fluids.CHLORINE, 125), new FluidStack(Fluids.NONE, 0), new ItemStack(ModItems.dust)));
		recipes.put(Fluids.CALCIUM_CHLORIDE, new ElectrolysisRecipe(250, new FluidStack(Fluids.CHLORINE, 125), new FluidStack(Fluids.CALCIUM_SOLUTION, 125)));
	}

	@Override
	public String getFileName() {
		return "hbmElectrolyzerFluid.json";
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
		
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		
	}

	public static class ElectrolysisRecipe {
		public FluidStack output1;
		public FluidStack output2;
		public int amount;
		public ItemStack[] byproduct;
		
		public ElectrolysisRecipe(int amount, FluidStack output1, FluidStack output2, ItemStack... byproduct) {
			this.output1 = output1;
			this.output2 = output2;
			this.amount = amount;
			this.byproduct = byproduct;
		}
	}
}
